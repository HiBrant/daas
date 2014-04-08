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

	public static List<MetadataEntity> retrieveByName(String appid,
			String modelName) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		List<MetadataEntity> list = repo.findByAppidAndModelName(appid,
				modelName);
		((ConfigurableApplicationContext) ctx).close();
		return list;
	}

	public static MetadataEntity retrieveOne(String appid, String modelName,
			int version) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		MetadataEntity meta = repo.findByAppidAndModelNameAndVersion(appid,
				modelName, version);
		((ConfigurableApplicationContext) ctx).close();
		return meta;
	}
}
