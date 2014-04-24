package data.as.a.service.convert.metadata;

import java.io.File;

import data.as.a.service.convert.Converter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.common.ModelClassMissingException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.util.ClassPathUtil;

public class Classpath2ReferModelObjectConverter implements
		Converter<String, DataModelObject, SystemException> {

	@Override
	public DataModelObject convert(String source)
			throws ModelClassMissingException {
		String classname = source.substring(
				source.lastIndexOf(ClassPathUtil.FILE_SEPERATOR) + 1)
				.replaceAll(ClassPathUtil.CLASS_EXTENSION, "");

		File file = new File(source);
		if (!file.isFile()) {
			throw new ModelClassMissingException(classname, null);
		}

		String[] strs = classname.split(ClassPathUtil.CLASSNAME_SEPERATOR);
		String version = strs[strs.length - 1];
		String appid = strs[strs.length - 2];
		String modelName = classname.substring(0, classname.indexOf(appid) - 8);

		return new DataModelObject(appid, modelName, null,
				Integer.parseInt(version));
	}
}
