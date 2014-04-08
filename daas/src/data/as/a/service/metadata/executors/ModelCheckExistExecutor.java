package data.as.a.service.metadata.executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.GenericMetadataExecutor;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.SemanticsType;

public class ModelCheckExistExecutor implements
		GenericMetadataExecutor<DataModelObject, Boolean> {

	@Override
	public Boolean execute(DataModelObject dmo) throws UserException,
			SystemException {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		MetadataEntity meta = repo.findByAppidAndModelNameAndVersion(
				dmo.getAppid(), dmo.getModelName(), dmo.getVersion());
		if (meta == null) {
			((ConfigurableApplicationContext) ctx).close();
			return false;
		}

		if (meta.getSemantics().equals(SemanticsType.ACID.name())) {
			dmo.setSemantics(SemanticsType.ACID);
		} else {
			dmo.setSemantics(SemanticsType.BASE);
		}
		((ConfigurableApplicationContext) ctx).close();
		return true;
	}

}
