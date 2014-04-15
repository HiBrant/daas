package data.as.a.service.metadata.executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.GenericMetadataExecutor;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.transaction.ModelDeleteTransaction;

public class ModelDeleteExecutor implements
		GenericMetadataExecutor<String, Boolean> {

	@Override
	public Boolean execute(String modelId) throws UserException,
			SystemException {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		ModelDeleteTransaction trans = ctx.getBean(ModelDeleteTransaction.class);
		trans.execute(modelId);
		return true;
	}

}
