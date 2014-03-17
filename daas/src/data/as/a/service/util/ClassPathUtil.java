package data.as.a.service.util;

import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.SemanticsType;

public class ClassPathUtil {

	public static final String BASE_PACKAGE = "data.as.a.service.access";
	private static final String CLASS_ROOT_PATH;
	static {
		String path = ClassPathUtil.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		path = path.substring(0, path.indexOf("WEB-INF") + 16);
		CLASS_ROOT_PATH = path;
	}
	private static final String ENTITY_JPA_SUB_PACKAGE = ".entity.jpa.";
	private static final String ENTITY_MONGO_SUB_PACKAGE = ".entity.mongo.";
	private static final String REPO_JPA_SUB_PACKAGE = ".repo.jpa.";
	private static final String REPO_MONGO_SUB_PACKAGE = ".repo.mongo.";

	public static final String DOT_SEPERATOR = ".";
	public static final String FILE_SEPERATOR = "/";
	public static final String CLASSNAME_SEPERATOR = "_";
	public static final String CLASS_EXTENSION = ".class";

	private static final String ENTITY = "entity";
	private static final String REPO = "repo";

	public static String getEntityJavaClasspath(DataModelObject dmo) {
		StringBuilder builder = new StringBuilder();
		builder.append(BASE_PACKAGE);
		if (dmo.getSemantics() == SemanticsType.ACID) {
			builder.append(ENTITY_JPA_SUB_PACKAGE);
		} else {
			builder.append(ENTITY_MONGO_SUB_PACKAGE);
		}

		builder.append(dmo.getModelName()).append(CLASSNAME_SEPERATOR)
				.append(ENTITY).append(CLASSNAME_SEPERATOR)
				.append(dmo.getAppid()).append(CLASSNAME_SEPERATOR)
				.append(dmo.getVersion());

		return builder.toString();
	}

	public static String getEntityJavaClasspathWithExtension(DataModelObject dmo) {
		return getEntityJavaClasspath(dmo) + CLASS_EXTENSION;
	}

	public static String getEntityFilePath(DataModelObject dmo) {
		StringBuilder builder = new StringBuilder(CLASS_ROOT_PATH);
		builder.append(
				getEntityJavaClasspath(dmo).replaceAll("\\" + DOT_SEPERATOR,
						FILE_SEPERATOR)).append(CLASS_EXTENSION);

		return builder.toString();
	}

	public static String getFilePathByClassFullName(String classFullName) {
		StringBuilder builder = new StringBuilder(CLASS_ROOT_PATH);
		builder.append(
				classFullName.replaceAll("\\" + DOT_SEPERATOR, FILE_SEPERATOR))
				.append(CLASS_EXTENSION);

		return builder.toString();
	}

	public static String getRepositoryJavaClasspathWithoutConditions(
			DataModelObject dmo) {
		StringBuilder builder = new StringBuilder();
		builder.append(BASE_PACKAGE);
		if (dmo.getSemantics() == SemanticsType.ACID) {
			builder.append(REPO_JPA_SUB_PACKAGE);
		} else {
			builder.append(REPO_MONGO_SUB_PACKAGE);
		}

		builder.append(dmo.getModelName()).append(CLASSNAME_SEPERATOR)
				.append(REPO).append(CLASSNAME_SEPERATOR)
				.append(dmo.getAppid()).append(CLASSNAME_SEPERATOR)
				.append(dmo.getVersion());

		return builder.toString();
	}

	public static String getRepositoryFilePathWithoutConditions(
			DataModelObject dmo) {
		StringBuilder builder = new StringBuilder(CLASS_ROOT_PATH);
		builder.append(
				getRepositoryJavaClasspathWithoutConditions(dmo).replaceAll(
						"\\" + DOT_SEPERATOR, FILE_SEPERATOR)).append(
				CLASS_EXTENSION);

		return builder.toString();
	}
}
