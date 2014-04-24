package data.as.a.service.convert.adaptor;

import java.lang.reflect.Field;

import net.sf.json.JSONObject;
import data.as.a.service.convert.Converter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.adaptor.FailToAccessEntityFieldException;

public class EntityObject2JSONConverter implements
		Converter<Object, JSONObject, SystemException> {

	@Override
	public JSONObject convert(Object source) throws SystemException {
		JSONObject json = new JSONObject();

		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				String name = field.getName();
				Object value = field.get(source);
				json.put(name, value);
			} catch (IllegalArgumentException e) {
				throw new FailToAccessEntityFieldException(source.getClass()
						.getName(), field.getName(), e);
			} catch (IllegalAccessException e) {
				throw new FailToAccessEntityFieldException(source.getClass()
						.getName(), field.getName(), e);
			}
		}

		return json;
	}

}
