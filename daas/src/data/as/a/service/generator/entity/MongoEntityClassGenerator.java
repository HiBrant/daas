package data.as.a.service.generator.entity;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import data.as.a.service.generator.Generator;
import data.as.a.service.generator.convert.FieldType2JavaTypeConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.Field;
import data.as.a.service.util.ClassPathUtil;

public class MongoEntityClassGenerator implements Generator {

	private DataModelObject dmo;

	public MongoEntityClassGenerator(DataModelObject dmo) {
		this.dmo = dmo;
	}

	@Override
	public byte[] generate() {

		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av;

		StringBuilder sb;
		FieldType2JavaTypeConverter tc = new FieldType2JavaTypeConverter();
		String classpath = ClassPathUtil.getEntityJavaClasspath(dmo)
				.replaceAll("\\" + ClassPathUtil.DOT_SEPERATOR,
						ClassPathUtil.FILE_SEPERATOR);

		cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, classpath, null,
				"java/lang/Object", null);

		{
			av = cw.visitAnnotation(
					"Lorg/springframework/data/mongodb/core/mapping/Document;",
					true);
			sb = new StringBuilder().append(dmo.getModelName())
					.append(ClassPathUtil.CLASSNAME_SEPERATOR)
					.append(dmo.getAppid())
					.append(ClassPathUtil.CLASSNAME_SEPERATOR)
					.append(dmo.getVersion());
			av.visit("collection", sb.toString());
			av.visitEnd();
		}

		for (Field field : dmo.getFields()) {
			fv = cw.visitField(ACC_PUBLIC, field.getName(),
					tc.convert(field.getType()), null, null);
			{
				av = fv.visitAnnotation(
						"Lorg/springframework/data/mongodb/core/mapping/Field;",
						true);
				av.visitEnd();
			}
			fv.visitEnd();
		}

		fv = cw.visitField(ACC_PUBLIC, "_id", "Ljava/lang/String;", null, null);
		{
			av = fv.visitAnnotation("Lorg/springframework/data/annotation/Id;",
					true);
			av.visitEnd();
		}
		{
			av = fv.visitAnnotation(
					"Lorg/springframework/data/mongodb/core/mapping/Field;",
					true);
			av.visitEnd();
		}
		fv.visitEnd();

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(8, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>",
					"()V");
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "L" + classpath + ";", null, l0, l1,
					0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}

		cw.visitEnd();

		return cw.toByteArray();
	}

}
