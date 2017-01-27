package lv.nixx.poc.camel.persistence;

import java.util.Collection;

import lv.nixx.poc.camel.model.Person;

public interface PersonDAO {
	
	Collection<Person> getAllPersons();
	void save(Person person);
	String getGreeting();

}
