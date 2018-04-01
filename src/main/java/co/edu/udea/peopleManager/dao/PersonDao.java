package co.edu.udea.peopleManager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.peopleManager.model.Person;

public interface PersonDao extends CrudRepository<Person, String>{
	
	Person findById(String id);
	
	@Query("SELECT p FROM Person p WHERE p.deleted = :deleted ORDER BY p.name DESC, p.registerDate DESC")
	List<Person> findAll(@Param("deleted") boolean deleted);
	
	List<Person> findByName(String name);
	
	List<Person> findByNameContaining(String name);
	
	@Transactional
	@Modifying
	@Query("UPDATE Person p SET p.deleted = true where p.id = :id")
	void deleteById(@Param("id") String id);

}
