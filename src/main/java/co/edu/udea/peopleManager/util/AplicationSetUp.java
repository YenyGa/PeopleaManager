package co.edu.udea.peopleManager.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.peopleManager.dao.PersonDao;
import co.edu.udea.peopleManager.model.Person;

@Component
public class AplicationSetUp {

	@Autowired
	PersonDao personDao;

	@PostConstruct
	public void setUp() {
		personDao.save(new Person("1234", "Daisy Jhonson", "Calle 84 No 43-33", "3466784", "daisyJho@shield.com"));
		personDao.save(new Person("1234567", "Steve Rogers", "Calle 21 No 53-33", "2345678", "steveRog@shield.com"));
		personDao.save(new Person("45678", "Phillip Coulson", "Calle 84 No 43-33", "6767897", "coulson@shield.com"));
	}
}
