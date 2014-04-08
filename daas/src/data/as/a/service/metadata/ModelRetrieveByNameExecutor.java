package data.as.a.service.metadata;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.convert.MetadataEntity2JSONConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.service.MetadataRetrieveService;

public class ModelRetrieveByNameExecutor implements MetadataExecutor {

	@Override
	public JSONObject execute(DataModelObject dmo) throws UserException,
			SystemException {

		List<MetadataEntity> entities = MetadataRetrieveService.retrieveByName(
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
}
