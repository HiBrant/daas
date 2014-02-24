package data.as.a.service.access.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import data.as.a.service.access.entity.jpa.Person;

public interface PersonRepository extends JpaRepository<Person, String> {
	public Person findByName(String name);
}

