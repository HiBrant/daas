package data.as.a.service.adaptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.Person;
import data.as.a.service.access.repo.jpa.PersonRepository;
import data.as.a.service.adaptor.config.MongoDBConfig;
import data.as.a.service.adaptor.config.MySQLJPAConfig;

public class TestAdaptor {
	public static Person findByName(String name) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MySQLJPAConfig.class);
		PersonRepository repo = ctx.getBean(PersonRepository.class);
		Person p = repo.findByName(name);
		((ConfigurableApplicationContext) ctx).close();
		return p;
	}

	public static void savePerson(String name) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MySQLJPAConfig.class);
		PersonRepository repo = ctx.getBean(PersonRepository.class);
		Person p = new Person();
		p.name = name;
		repo.save(p);
		((ConfigurableApplicationContext) ctx).close();
	}

	public static void savePersonInMongo(String name) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MongoDBConfig.class);
		data.as.a.service.access.repo.mongo.PersonRepository repo = ctx
				.getBean(data.as.a.service.access.repo.mongo.PersonRepository.class);
		data.as.a.service.access.entity.mongo.Person person = 
				new data.as.a.service.access.entity.mongo.Person();
		person.name = name;
		repo.save(person);
		((ConfigurableApplicationContext) ctx).close();
	}
}
