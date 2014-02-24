package data.as.a.service.access.repo.mongo;

import org.springframework.data.repository.CrudRepository;

import data.as.a.service.access.entity.mongo.Person;

public interface PersonRepository extends CrudRepository<Person, String> {

}
