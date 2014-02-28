package data.as.a.service.metadata.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.generator.classloader.GeneratorClassLoader;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.convert.DataModel2MetadataEntityConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.SemanticsType;
import data.as.a.service.metadata.exception.ModelClassMissingException;
import data.as.a.service.metadata.exception.ModelExistsException;
import data.as.a.service.util.ClassPathUtil;

public class MetadataOperator {

	public static MetadataEntity saveModelIntoDatabase(DataModelObject dmo)
			throws ModelExistsException {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		MetadataEntity meta = repo.findByAppidAndModelNameAndVersion(
				dmo.getAppid(), dmo.getModelName(), dmo.getVersion());
		if (meta != null) {
			throw new ModelExistsException(dmo.getModelName(), dmo.getVersion());
		}

		meta = new DataModel2MetadataEntityConverter().convert(dmo);

		meta = repo.save(meta);
		((ConfigurableApplicationContext) ctx).close();

		return meta;
	}

	public static Class<?> getEntityClass(DataModelObject dmo)
			throws ModelClassMissingException {

		Class<?> clazz = null;

		String classname = ClassPathUtil.getEntityJavaClasspath(dmo);
		try {
			clazz = Class.forName(classname, true, new GeneratorClassLoader());
		} catch (ClassNotFoundException e) {
			throw new ModelClassMissingException(classname, e);
		}

		return clazz;
	}

	public static boolean dataModelExists(DataModelObject dmo) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		MetadataEntity meta = repo.findByAppidAndModelNameAndVersion(
				dmo.getAppid(), dmo.getModelName(), dmo.getVersion());
		if (meta == null) {
			((ConfigurableApplicationContext) ctx).close();
			return false;
		}

		if (meta.getSemantics().equals(SemanticsType.ACID.name())) {
			dmo.setSemantics(SemanticsType.ACID);
		} else {
			dmo.setSemantics(SemanticsType.BASE);
		}
		((ConfigurableApplicationContext) ctx).close();
		return true;
	}

}
