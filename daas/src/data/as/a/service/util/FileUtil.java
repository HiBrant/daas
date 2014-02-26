package data.as.a.service.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

	public static void save(String pathname, byte[] b) throws IOException {
		File file = new File(pathname);
		File parent = file.getParentFile();
		if (!parent.isDirectory()) {
			parent.mkdirs();
		}
		BufferedOutputStream os = new BufferedOutputStream(
				new FileOutputStream(file));
		os.write(b);
		os.flush();
		os.close();
	}
}
