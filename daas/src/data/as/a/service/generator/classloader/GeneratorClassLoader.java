package data.as.a.service.generator.classloader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import data.as.a.service.util.ClassPathUtil;

public class GeneratorClassLoader extends ClassLoader {

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		
		Class<?> clazz = this.findLoadedClass(name);
		if (clazz != null) {
			return clazz;
		}

		String classFilePath = ClassPathUtil
				.getEntityFilePathByClassFullName(name);System.out.println(classFilePath);
		byte[] b = null;
		try {
			BufferedInputStream is = new BufferedInputStream(
					new FileInputStream(classFilePath));
			int fileSize = is.available();
			b = new byte[fileSize];
			is.read(b);
			is.close();
		} catch (FileNotFoundException e) {
			throw new ClassNotFoundException(name, e);
		} catch (IOException e) {
			throw new ClassNotFoundException(name, e);
		}

		return this.defineClass(name, b, 0, b.length);
	}

}
