package data.as.a.service.metadata.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.convert.DataModel2MetadataEntityConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.exception.ModelExistsException;

public class MetadataCreateService {

	public static MetadataEntity saveModelIntoDatabase(DataModelObject dmo)
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
