package data.as.a.service.util;

import data.as.a.service.exception.common.ModelClassMissingException;
import data.as.a.service.metadata.datamodel.DataModelObject;

public class ClassUtil {
	
	public static Class<?> getEntityClass(DataModelObject dmo)
			throws ModelClassMissingException {

		Class<?> clazz = null;

		String classname = ClassPathUtil.getEntityJavaClasspath(dmo);
		try {
			clazz = Class.forName(classname);
		} catch (ClassNotFoundException e) {
			throw new ModelClassMissingException(classname, e);
		}

		return clazz;
	}
}
