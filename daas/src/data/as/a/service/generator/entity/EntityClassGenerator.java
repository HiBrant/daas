package data.as.a.service.generator.entity;

import java.io.IOException;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.generator.FailToLoadClassException;
import data.as.a.service.exception.generator.FailToSaveClassFileException;
import data.as.a.service.generator.Generator;
import data.as.a.service.generator.classloader.GeneratorClassLoader;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.SemanticsType;
import data.as.a.service.util.ClassPathUtil;
import data.as.a.service.util.FileUtil;

public class EntityClassGenerator {

	public static void generate(DataModelObject dmo) throws SystemException {

		Generator generator = null;
		if (dmo.getSemantics() == SemanticsType.ACID) {
			generator = new JPAEntityClassGenerator(dmo);
		} else {
			generator = new MongoEntityClassGenerator(dmo);
		}

		try {
			FileUtil.save(ClassPathUtil.getEntityFilePath(dmo),
					generator.generate());
		} catch (IOException e) {
			throw new FailToSaveClassFileException(
					ClassPathUtil.getEntityJavaClasspath(dmo), e);
		}

		try {
			Class.forName(ClassPathUtil.getEntityJavaClasspath(dmo), true,
					new GeneratorClassLoader());
		} catch (ClassNotFoundException e) {
			throw new FailToLoadClassException(
					ClassPathUtil.getEntityJavaClasspath(dmo), e);
		}
	}
}
