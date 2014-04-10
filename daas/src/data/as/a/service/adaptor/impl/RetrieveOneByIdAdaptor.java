package data.as.a.service.adaptor.impl;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.ApplicationContext;

import net.sf.json.JSONObject;
import data.as.a.service.adaptor.Adaptor;
import data.as.a.service.adaptor.config.ApplicationContextHolder;
import data.as.a.service.adaptor.convert.EntityObject2JSONConverter;
import data.as.a.service.adaptor.exception.FailToCallRepositoryMethodException;
import data.as.a.service.adaptor.exception.NoDataObjectInstanceReferedException;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.generator.exception.FailToLoadClassException;
import data.as.a.service.generator.repo.RepositoryClassGenerator;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.util.ClassPathUtil;

public class RetrieveOneByIdAdaptor implements Adaptor<String, JSONObject> {

	@Override
	public JSONObject execute(DataModelObject dmo, String _id)
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
		Object entity = null;
		try {
			entity = repo.getClass()
					.getDeclaredMethod(METHOD_FIND_ONE, Serializable.class)
					.invoke(repo, _id);
			if (entity == null) {
				throw new NoDataObjectInstanceReferedException(
						dmo.getModelName(), dmo.getVersion(), _id);
			}
			
		} catch (IllegalAccessException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ONE, e);
		} catch (IllegalArgumentException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ONE, e);
		} catch (InvocationTargetException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ONE, e);
		} catch (NoSuchMethodException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ONE, e);
		} catch (SecurityException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ONE, e);
		}
		ctxHolder.close();

		return new EntityObject2JSONConverter().convert(entity);
	}

}
