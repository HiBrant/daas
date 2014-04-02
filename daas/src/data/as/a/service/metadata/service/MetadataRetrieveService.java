package data.as.a.service.metadata.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.metadata.config.MetadataAccessConfig;

public class MetadataRetrieveService {

	public static List<MetadataEntity> retrieveAll(String appid) {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		List<MetadataEntity> list = repo.findByAppid(appid);
		((ConfigurableApplicationContext) ctx).close();
		return list;
	}
}
