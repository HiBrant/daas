package data.as.a.service.convert.adaptor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import data.as.a.service.convert.Converter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.adaptor.FailToAssignEntityFieldException;
import data.as.a.service.exception.adaptor.FailToInstantiateEntityObjectException;
import data.as.a.service.exception.adaptor.FieldTypeNotMatchDataModelException;
import data.as.a.service.exception.adaptor.NoSuchFieldDefinedException;

public class JSON2EntityObjectConverter implements
		Converter<JSONObject, Object, Throwable> {

	private Class<?> clazz;

	public JSON2EntityObjectConverter(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Object convert(JSONObject source) throws SystemException,
			UserException {

		Object entity = null;

		try {
			entity = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new FailToInstantiateEntityObjectException(clazz.getName(), e);
		} catch (IllegalAccessException e) {
			throw new FailToInstantiateEntityObjectException(clazz.getName(), e);
		}
		
		JSONArray names = source.names();
		String name = null;
		for (int i = 0; i < names.size(); i++) {
			name = names.getString(i);
			Object value = source.get(name);
			
			try {
				clazz.getDeclaredField(name).set(entity, value);
			} catch (IllegalArgumentException e) {
				throw new FieldTypeNotMatchDataModelException(name);
			} catch (IllegalAccessException e) {
				throw new FailToAssignEntityFieldException(clazz.getName(), name, e);
			} catch (NoSuchFieldException e) {
				throw new NoSuchFieldDefinedException(name);
			} catch (SecurityException e) {
				throw new FailToAssignEntityFieldException(clazz.getName(), name, e);
			}
		}

		return entity;
	}
	
}
