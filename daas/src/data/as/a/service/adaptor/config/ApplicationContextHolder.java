package data.as.a.service.adaptor.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.metadata.datamodel.SemanticsType;

public class ApplicationContextHolder {

	private ApplicationContext ctx = null;

	public ApplicationContextHolder(SemanticsType semantics) {
		Class<?> configClass = null;
		if (semantics == SemanticsType.ACID) {
			configClass = MySQLJPAConfig.class;
		} else {
			configClass = MongoDBConfig.class;
		}

		ctx = new AnnotationConfigApplicationContext(configClass);
	}
	
	public ApplicationContext getApplicationContext() {
		return ctx;
	}
	
	public void close() {
		if (ctx != null) {
			((ConfigurableApplicationContext) ctx).close();
		}
	}
}
