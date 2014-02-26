package data.as.a.service.metadata.service;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.generator.entity.EntityClassGenerator;
import data.as.a.service.generator.exception.FailToSaveClassFileException;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.convert.DataModel2MetadataEntityConverter;
import data.as.a.service.metadata.convert.MetadataEntity2JSONConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.exception.ModelClassMissingException;
import data.as.a.service.metadata.exception.ModelExistsException;

public class ModelDefinitionExecutor implements MetadataExecutor {

	@Override
	public JSONObject execute(DataModelObject dmo) throws ModelExistsException,
			FailToSaveClassFileException, ModelClassMissingException {

		MetadataEntity meta = this.saveModelIntoDatabase(dmo);
		EntityClassGenerator.generate(dmo);
		return new MetadataEntity2JSONConverter().convert(meta);
	}

	private MetadataEntity saveModelIntoDatabase(DataModelObject dmo)
			throws ModelExistsException {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		MetadataEntity meta = repo.findByAppidAndModelNameAndVersion(
				dmo.getAppid(), dmo.getModelName(), dmo.getVersion());
		if (meta != null) {
			throw new ModelExistsException(dmo.getModelName(), dmo.getVersion());
		}

		meta = new DataModel2MetadataEntityConverter().convert(dmo);

		meta = repo.save(meta);
		((ConfigurableApplicationContext) ctx).close();

		return meta;
	}
}
