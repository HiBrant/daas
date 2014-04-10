package data.as.a.service.adaptor.convert;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import data.as.a.service.convert.Converter;
import data.as.a.service.exception.SystemException;

public class EntityList2JSONConverter implements Converter<List<?>, JSONObject, Throwable> {

	@Override
	public JSONObject convert(List<?> source) throws SystemException {
		
		JSONObject result = new JSONObject();
		result.put("count", source.size());
		JSONArray list = new JSONArray();
		EntityObject2JSONConverter converter = new EntityObject2JSONConverter();
		for (Object entity: source) {
			JSONObject json = converter.convert(entity);
			list.add(json);
		}
		result.put("list", list);
		return result;
	}

}
