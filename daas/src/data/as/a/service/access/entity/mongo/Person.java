package data.as.a.service.access.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "person")
public class Person {

	@Id
	@Field
	public String _id;
	
	@Field
	public String name;
	
//	@Field
//	public String time = format.format(new Date());
//	
//	private static SimpleDateFormat format = new SimpleDateFormat(
//			"yyyy-MM-dd HH:mm:ss");
//
//	@Override
//	public String toString() {
//		return new StringBuilder("{_id=\"").append(_id).append("\", name=\"")
//				.append(name).append("\", time=\"").append(time).append("\"}")
//				.toString();
//	}
}
