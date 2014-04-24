package data.as.a.service.adaptor.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import net.sf.json.JSONObject;
import data.as.a.service.adaptor.Adaptor;
import data.as.a.service.adaptor.condition.Conditions;
import data.as.a.service.adaptor.config.ApplicationContextHolder;
import data.as.a.service.convert.adaptor.EntityList2JSONConverter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.adaptor.FailToCallRepositoryMethodException;
import data.as.a.service.exception.generator.FailToLoadClassException;
import data.as.a.service.generator.repo.RepositoryClassGenerator;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.util.ClassPathUtil;

public class RetrieveBatchAdaptor implements Adaptor<Conditions, JSONObject> {

	@Override
	public JSONObject execute(DataModelObject dmo, Conditions conditions)
			throws SystemException, UserException {

		if (conditions == null) {
			return organize(findAll(dmo));
		} else {
			List<?> list = findAll(dmo);
			List<Object> result = new ArrayList<>();
			for (Object entity: list) {
				if (conditions.judge(entity)) {
					result.add(entity);
				}
			}
			return organize(result);
		}

	}

	private JSONObject organize(List<?> list) throws SystemException,
			UserException {

		return new EntityList2JSONConverter().convert(list);
	}

	private List<?> findAll(DataModelObject dmo) throws SystemException,
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
		List<?> list = null;
		try {
			list = (List<?>) repo.getClass().getDeclaredMethod(METHOD_FIND_ALL)
					.invoke(repo);
		} catch (IllegalAccessException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ALL, e);
		} catch (IllegalArgumentException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ALL, e);
		} catch (InvocationTargetException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ALL, e);
		} catch (NoSuchMethodException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ALL, e);
		} catch (SecurityException e) {
			throw new FailToCallRepositoryMethodException(repoClass,
					METHOD_FIND_ALL, e);
		}
		ctxHolder.close();
		
		return list;
	}
}
