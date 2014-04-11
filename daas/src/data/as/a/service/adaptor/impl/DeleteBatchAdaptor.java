package data.as.a.service.adaptor.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.ApplicationContext;

import data.as.a.service.adaptor.Adaptor;
import data.as.a.service.adaptor.condition.Conditions;
import data.as.a.service.adaptor.config.ApplicationContextHolder;
import data.as.a.service.adaptor.exception.FailToCallRepositoryMethodException;
import data.as.a.service.exception.StillDevelopingException;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.generator.exception.FailToLoadClassException;
import data.as.a.service.generator.repo.RepositoryClassGenerator;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.util.ClassPathUtil;

public class DeleteBatchAdaptor implements Adaptor<Conditions, Boolean> {

	@Override
	public Boolean execute(DataModelObject dmo, Conditions conditions)
			throws SystemException, UserException {

		if (conditions == null) {
			return this.deleteAll(dmo);
		} else {
			throw new StillDevelopingException(DeleteBatchAdaptor.class,
					"execute", "Query conditions are not null");
		}
	}

	private boolean deleteAll(DataModelObject dmo) throws SystemException,
			UserException {

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
		try {
			method = METHOD_DELETE_ALL;
			repo.getClass().getDeclaredMethod(method).invoke(repo);
			
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

		return true;
	}

}
