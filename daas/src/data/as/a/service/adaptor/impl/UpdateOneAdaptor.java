package data.as.a.service.adaptor.impl;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.ApplicationContext;

import data.as.a.service.adaptor.Adaptor;
import data.as.a.service.adaptor.config.ApplicationContextHolder;
import data.as.a.service.adaptor.convert.EntityObject2JSONConverter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.adaptor.FailToAssignEntityFieldException;
import data.as.a.service.exception.adaptor.FailToCallRepositoryMethodException;
import data.as.a.service.exception.adaptor.FieldTypeNotMatchDataModelException;
import data.as.a.service.exception.adaptor.NoDataObjectInstanceReferedException;
import data.as.a.service.exception.adaptor.NoSuchFieldDefinedException;
import data.as.a.service.exception.generator.FailToLoadClassException;
import data.as.a.service.generator.repo.RepositoryClassGenerator;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.util.ClassPathUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateOneAdaptor implements Adaptor<JSONObject, JSONObject> {

	@Override
	public JSONObject execute(DataModelObject dmo, JSONObject updateJson)
			throws SystemException, UserException {

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
		String method = null;
		Object entity = null;
		try {
			method = METHOD_FIND_ONE;
			String _id = updateJson.getString("_id");
			entity = repo.getClass()
					.getDeclaredMethod(method, Serializable.class)
					.invoke(repo, _id);
			if (entity == null) {
				throw new NoDataObjectInstanceReferedException(
						dmo.getModelName(), dmo.getVersion(), _id);
			}

			this.updateEntityAttributes(entity, updateJson);

			method = METHOD_SAVE;
			entity = repo.getClass().getDeclaredMethod(method, Object.class)
					.invoke(repo, entity);
		} catch (IllegalAccessException e) {
			throw new FailToCallRepositoryMethodException(repoClass, method, e);
		} catch (IllegalArgumentException e) {
			throw new FailToCallRepositoryMethodException(repoClass, method, e);
		} catch (InvocationTargetException e) {
			throw new FailToCallRepositoryMethodException(repoClass, method, e);
		} catch (NoSuchMethodException e) {
			throw new FailToCallRepositoryMethodException(repoClass, method, e);
		} catch (SecurityException e) {
			throw new FailToCallRepositoryMethodException(repoClass, method, e);
		}
		ctxHolder.close();

		return new EntityObject2JSONConverter().convert(entity);
	}

	private void updateEntityAttributes(Object entity, JSONObject updateJson)
			throws UserException, SystemException {

		JSONArray names = updateJson.names();
		String name = null;
		for (int i = 0; i < names.size(); i++) {
			name = names.getString(i);
			if (name.equals("_id")) {
				continue;
			}
			Object value = updateJson.get(name);

			Class<?> clazz = entity.getClass();
			try {
				clazz.getDeclaredField(name).set(entity, value);
			} catch (IllegalArgumentException e) {
				throw new FieldTypeNotMatchDataModelException(name);
			} catch (IllegalAccessException e) {
				throw new FailToAssignEntityFieldException(clazz.getName(),
						name, e);
			} catch (NoSuchFieldException e) {
				throw new NoSuchFieldDefinedException(name);
			} catch (SecurityException e) {
				throw new FailToAssignEntityFieldException(clazz.getName(),
						name, e);
			}
		}
	}

}