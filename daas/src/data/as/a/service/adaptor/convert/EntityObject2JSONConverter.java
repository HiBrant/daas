package data.as.a.service.adaptor.convert;

import java.lang.reflect.Field;

import net.sf.json.JSONObject;
import data.as.a.service.convert.Converter;
import data.as.a.service.exception.SystemException;

public class EntityObject2JSONConverter implements
		Converter<Object, JSONObject, SystemException> {

	@Override
	public JSONObject convert(Object source) throws SystemException {
		JSONObject json = new JSONObject();
		
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field: fields) {
			try {
				String name = field.getName();
				Object value = field.get(source);
				json.put(name, value);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return json;
	}

}
