package data.as.a.service.management;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import data.as.a.service.access.entity.jpa.sys.AppEntity;
import data.as.a.service.access.repo.jpa.sys.AppRepository;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.util.DateFormatter;
import data.as.a.service.util.MD5Util;

public class ApplicationService {

	public AppEntity createOne(String appName, String userId) {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		AppRepository repo = ctx.getBean(AppRepository.class);
		AppEntity app = repo.findByAppNameAndUserId(appName, userId);
		if (app != null) {
			app = null;
		} else {
			app = new AppEntity();
			app.setAppName(appName);
			app.setUserId(userId);
			app.setTimestamp(new Date().getTime());
			app.setApiKey(MD5Util.get32bit(userId + appName + DateFormatter.now()));
			app = repo.save(app);
		}
		((ConfigurableApplicationContext) ctx).close();
		return app;
	}
	
	public List<AppEntity> getAllApps(String userId) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		AppRepository repo = ctx.getBean(AppRepository.class);
		Order order = new Order(Direction.DESC, "timestamp");
		Sort sort = new Sort(order);
		List<AppEntity> list = repo.findAll(sort);
		((ConfigurableApplicationContext) ctx).close();
		return list;
	}
}
