package data.as.a.service.metadata.executors;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.MetadataExecutor;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.convert.MetadataEntity2JSONConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;

public class ModelRetrieveByNameExecutor implements MetadataExecutor {

	@Override
	public JSONObject execute(DataModelObject dmo) throws UserException,
			SystemException {

		List<MetadataEntity> entities = this.retrieveByName(
				dmo.getAppid(), dmo.getModelName());
		JSONArray array = new JSONArray();
		MetadataEntity2JSONConverter converter = new MetadataEntity2JSONConverter();
		for (MetadataEntity entity : entities) {
			array.add(converter.convert(entity));
		}
		JSONObject result = new JSONObject();
		result.put("count", entities.size());
		result.put("list", array);
		return result;
	}
	
	private List<MetadataEntity> retrieveByName(String appid,
			String modelName) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		List<MetadataEntity> list = repo.findByAppidAndModelName(appid,
				modelName);
		((ConfigurableApplicationContext) ctx).close();
		return list;
	}
}
