package data.as.a.service.metadata.executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.sys.AppEntity;
import data.as.a.service.access.repo.jpa.sys.AppRepository;
import data.as.a.service.metadata.GenericMetadataExecutor;
import data.as.a.service.metadata.config.MetadataAccessConfig;

public class AppVerifyExecutor implements
		GenericMetadataExecutor<AppEntity, Boolean> {

	@Override
	public Boolean execute(AppEntity app) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		AppRepository repo = ctx.getBean(AppRepository.class);
		AppEntity tmp = repo.findOne(app.get_id());
		boolean result = true;
		if (tmp == null || !tmp.getApiKey().equals(app.getApiKey())) {
			result = false;
		}
		((ConfigurableApplicationContext) ctx).close();
		return result;
	}

}
