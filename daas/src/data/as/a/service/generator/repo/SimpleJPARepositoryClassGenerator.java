package data.as.a.service.generator.repo;

import org.objectweb.asm.ClassWriter;

import data.as.a.service.generator.Generator;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.util.ClassPathUtil;

public class SimpleJPARepositoryClassGenerator implements Generator {

	private DataModelObject dmo;

	public SimpleJPARepositoryClassGenerator(DataModelObject dmo) {
		this.dmo = dmo;
	}

	@Override
	public byte[] generate() {

		ClassWriter cw = new ClassWriter(0);

		String repoClasspath = ClassPathUtil
				.getRepositoryJavaClasspathWithoutConditions(dmo).replaceAll(
						"\\" + ClassPathUtil.DOT_SEPERATOR,
						ClassPathUtil.FILE_SEPERATOR);
		String entityClasspath = ClassPathUtil.getEntityJavaClasspath(dmo)
				.replaceAll("\\" + ClassPathUtil.DOT_SEPERATOR,
						ClassPathUtil.FILE_SEPERATOR);
		
		cw.visit(
				V1_7,
				ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
				repoClasspath,
				"Ljava/lang/Object;Lorg/springframework/data/jpa/repository/JpaRepository<L"
						+ entityClasspath + ";Ljava/lang/String;>;",
				"java/lang/Object",
				new String[] { "org/springframework/data/jpa/repository/JpaRepository" });
		cw.visitEnd();
		
		return cw.toByteArray();
	}

}
