package data.as.a.service.access.entity.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Person_999999_1")
public class Person_entity_999999_1 {
	
	@Id
	@GenericGenerator(name = "id_generator", strategy = "uuid")
	@GeneratedValue(generator = "id_generator")
	public String _id;
	
	@Column
	public String name;
	
	@Column
	public int age;

}
