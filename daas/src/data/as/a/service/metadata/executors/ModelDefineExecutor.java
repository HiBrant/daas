package data.as.a.service.metadata.executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.sf.json.JSONObject;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.convert.metadata.DataModel2MetadataEntityConverter;
import data.as.a.service.convert.metadata.MetadataEntity2JSONConverter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.metadata.ModelExistsException;
import data.as.a.service.exception.metadata.SameNameWithDiscardModelException;
import data.as.a.service.generator.entity.EntityClassGenerator;
import data.as.a.service.metadata.MetadataExecutor;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.datamodel.DataModelObject;

public class ModelDefineExecutor implements MetadataExecutor {

	@Override
	public JSONObject execute(DataModelObject dmo) throws UserException,
			SystemException {

		MetadataEntity meta = this.saveModelIntoDatabase(dmo);
		EntityClassGenerator.generate(dmo);
		return new MetadataEntity2JSONConverter().convert(meta);
	}

	private MetadataEntity saveModelIntoDatabase(DataModelObject dmo)
			throws UserException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		MetadataEntity meta = repo.findByAppidAndModelNameAndVersion(
				dmo.getAppid(), dmo.getModelName(), dmo.getVersion());
		if (meta != null) {
			if (meta.isDiscard()) {
				throw new SameNameWithDiscardModelException(dmo.getModelName(),
						dmo.getVersion());
			}

			throw new ModelExistsException(dmo.getModelName(), dmo.getVersion());
		}

		meta = new DataModel2MetadataEntityConverter().convert(dmo);

		meta = repo.save(meta);
		((ConfigurableApplicationContext) ctx).close();

		return meta;
	}
}
