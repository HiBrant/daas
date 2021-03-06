package data.as.a.service.adaptor.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.ApplicationContext;

import net.sf.json.JSONObject;
import data.as.a.service.adaptor.Adaptor;
import data.as.a.service.adaptor.config.ApplicationContextHolder;
import data.as.a.service.convert.adaptor.EntityObject2JSONConverter;
import data.as.a.service.convert.adaptor.JSON2EntityObjectConverter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.adaptor.FailToCallRepositoryMethodException;
import data.as.a.service.exception.generator.FailToLoadClassException;
import data.as.a.service.generator.repo.RepositoryClassGenerator;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.util.ClassPathUtil;
import data.as.a.service.util.ClassUtil;

public class CreateOneAdaptor implements Adaptor<JSONObject, JSONObject> {

	@Override
	public JSONObject execute(DataModelObject dmo, JSONObject entityJson)
			throws SystemException, UserException {

		Class<?> entityClass = ClassUtil.getEntityClass(dmo);

		JSON2EntityObjectConverter converter = new JSON2EntityObjectConverter(
				entityClass);
		Object entity = converter.convert(JSONObject.fromObject(entityJson));

		String repoFilepath = ClassPathUtil
				.getRepositoryFilePathWithoutConditions(dmo);
		File file = new File(repoFilepath);
		if (!file.isFile()) {
			RepositoryClassGenerator.generate(dmo);
		}

		Class<?> repoClass = null;
		try {
			repoClass = Class.forName(ClassPathUtil
					.getRepositoryJavaClasspathWithoutConditions(dmo));
		} catch (ClassNotFoundException e) {
			throw new FailToLoadClassException(
					ClassPathUtil
							.getRepositoryJavaClasspathWithoutConditions(dmo),
					e);
		}

		ApplicationContextHolder ctxHolder = new ApplicationContextHolder(
				dmo.getSemantics());
		ApplicationContext ctx = ctxHolder.getApplicationContext();

		Object repo = ctx.getBean(repoClass);
		try {
			entity = repo.getClass().getDeclaredMethod(METHOD_SAVE, Object.class)
					.invoke(repo, entity);
		} catch (IllegalAccessException e) {
			throw new FailToCallRepositoryMethodException(repoClass, METHOD_SAVE, e);
		} catch (IllegalArgumentException e) {
			throw new FailToCallRepositoryMethodException(repoClass, METHOD_SAVE, e);
		} catch (InvocationTargetException e) {
			throw new FailToCallRepositoryMethodException(repoClass, METHOD_SAVE, e);
		} catch (NoSuchMethodException e) {
			throw new FailToCallRepositoryMethodException(repoClass, METHOD_SAVE, e);
		} catch (SecurityException e) {
			throw new FailToCallRepositoryMethodException(repoClass, METHOD_SAVE, e);
		}
		ctxHolder.close();

		return new EntityObject2JSONConverter().convert(entity);
	}
}
