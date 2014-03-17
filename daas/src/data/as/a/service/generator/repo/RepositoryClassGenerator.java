package data.as.a.service.generator.repo;

import java.io.IOException;

import data.as.a.service.exception.SystemException;
import data.as.a.service.generator.Generator;
import data.as.a.service.generator.classloader.GeneratorClassLoader;
import data.as.a.service.generator.exception.FailToLoadClassException;
import data.as.a.service.generator.exception.FailToSaveClassFileException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.SemanticsType;
import data.as.a.service.util.ClassPathUtil;
import data.as.a.service.util.FileUtil;

public class RepositoryClassGenerator {

	public static void generate(DataModelObject dmo) throws SystemException {

		Generator generator = null;
		if (dmo.getSemantics() == SemanticsType.ACID) {
			generator = new SimpleJPARepositoryClassGenerator(dmo);
		} else {
			generator = new SimpleMongoRepositoryClassGenerator(dmo);
		}

		try {
			FileUtil.save(
					ClassPathUtil.getRepositoryFilePathWithoutConditions(dmo),
					generator.generate());
		} catch (IOException e) {
			throw new FailToSaveClassFileException(
					ClassPathUtil
							.getRepositoryJavaClasspathWithoutConditions(dmo),
					e);
		}

		try {
			Class.forName(ClassPathUtil
					.getRepositoryJavaClasspathWithoutConditions(dmo), true,
					new GeneratorClassLoader());
		} catch (ClassNotFoundException e) {
			throw new FailToLoadClassException(
					ClassPathUtil
							.getRepositoryJavaClasspathWithoutConditions(dmo),
					e);
		}
	}
}
