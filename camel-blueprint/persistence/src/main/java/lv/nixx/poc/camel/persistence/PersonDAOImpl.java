package lv.nixx.poc.camel.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.nixx.poc.camel.model.Person;

public class PersonDAOImpl implements PersonDAO {
	
	private static Logger log = LoggerFactory.getLogger(PersonDAOImpl.class);
	
	private static Map<Integer, Person> persons = new HashMap<>();
	static {
		persons.put(1, new Person(1, "name1", "surname1"));
		persons.put(2, new Person(2, "name2", "surname2"));
		persons.put(3, new Person(3, "name3", "surname3"));
		persons.put(4, new Person(4, "name4", "surname4"));
		persons.put(5, new Person(5, "name5", "surname5"));
	}
	
	
	@Override
	public Collection<Person> getAllPersons() {
		log.debug("get all persons");
		return persons.values();
	}

	@Override
	public void save(Person person) {
		log.debug("save person");
	}

	@Override
	public String getGreeting() {
		return "Hello my dear friend";
	}
	
}
