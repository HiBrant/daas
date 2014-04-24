package data.as.a.service.metadata.executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.sf.json.JSONObject;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.common.ModelNotAvailableException;
import data.as.a.service.metadata.MetadataExecutor;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.convert.MetadataEntity2JSONConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;

public class ModelRetrieveOneExecutor implements MetadataExecutor {

	@Override
	public JSONObject execute(DataModelObject dmo) throws UserException,
			SystemException {

		MetadataEntity meta = this.retrieveOne(
				dmo.getAppid(), dmo.getModelName(), dmo.getVersion());
		if (meta == null) {
			throw new ModelNotAvailableException(dmo.getModelName(),
					dmo.getVersion());
		}
		return new MetadataEntity2JSONConverter().convert(meta);
	}

	private MetadataEntity retrieveOne(String appid, String modelName,
			int version) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		MetadataEntity meta = repo.findByAppidAndModelNameAndVersion(appid,
				modelName, version);
		((ConfigurableApplicationContext) ctx).close();
		return meta;
	}
}
