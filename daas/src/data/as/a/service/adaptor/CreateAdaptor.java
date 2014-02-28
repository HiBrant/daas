package data.as.a.service.adaptor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.sf.json.JSONObject;
import data.as.a.service.adaptor.config.MongoDBConfig;
import data.as.a.service.adaptor.config.MySQLJPAConfig;
import data.as.a.service.adaptor.convert.EntityObject2JSONConverter;
import data.as.a.service.adaptor.convert.JSON2EntityObjectConverter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.SemanticsType;
import data.as.a.service.util.ClassPathUtil;

public class CreateAdaptor {

	public JSONObject execute(DataModelObject dmo, Class<?> entityClass,
			JSONObject entityJson) throws SystemException, UserException {

		JSON2EntityObjectConverter converter = new JSON2EntityObjectConverter(
				entityClass);
		Object entity = converter.convert(JSONObject.fromObject(entityJson));

		String repoFilepath = ClassPathUtil
				.getRepositoryFilePathWithoutConditions(dmo);
		File file = new File(repoFilepath);
		if (!file.isFile()) {
			// TODO Repository generation
		}

		Class<?> repoClass = null;
		try {
			repoClass = Class.forName(ClassPathUtil
					.getRepositoryJavaClasspathWithoutConditions(dmo));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Class<?> configClass = null;
		if (dmo.getSemantics() == SemanticsType.ACID) {
			configClass = MySQLJPAConfig.class;
		} else {
			configClass = MongoDBConfig.class;
		}

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				configClass);

		Object repo = ctx.getBean(repoClass);
		try {
			entity = repo.getClass().getDeclaredMethod("save", Object.class)
					.invoke(repo, entity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((ConfigurableApplicationContext) ctx).close();

		return new EntityObject2JSONConverter().convert(entity);
	}
}
