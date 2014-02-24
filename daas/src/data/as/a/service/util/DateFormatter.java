package data.as.a.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String now() {
		return format.format(new Date());
	}
}
