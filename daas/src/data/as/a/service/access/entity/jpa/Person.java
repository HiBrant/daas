package data.as.a.service.access.entity.jpa;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Person {
	@Id
	@GenericGenerator(name = "id_generator", strategy = "uuid")
	@GeneratedValue(generator = "id_generator")
	public String _id;

	@Column
	public String name;

	@Column
	public String time = format.format(new Date());

	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public String toString() {
		return new StringBuilder("{_id=\"").append(_id).append("\", name=\"")
				.append(name).append("\", time=\"").append(time).append("\"}")
				.toString();
	}
}
