package co.edu.udea.peopleManager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.peopleManager.model.Person;
import co.edu.udea.peopleManager.model.Product;

public interface PersonDao extends CrudRepository<Person, String>{
	
	Product findById(String id);
	
	List<Person> findAll();
	
	List<Product> findByName(String name);
	
	List<Product> findByNameContaining(String name);
	
//	@Transactional
//	@Modifying
//	@Query("UPDATE Product p SET p.amount = :amount where p.id = :id")
//	int updateAmountById(@Param("amount") int amount, @Param("id") String id);
//	
//	@Transactional
//	@Modifying
//	@Query("UPDATE Product p SET p.amount = :amount, p.name = :name, p.description = :description, p.price = :price where p.id = :id")
//	int updateProduct(@Param("id") String id, @Param("amount") int amount, @Param("name") String name, 
//			@Param("description") String description, @Param("price") int price);
	
	@Transactional
	@Modifying
	@Query("UPDATE Person p SET p.deleted = :deleted where p.id = :id")
	void deleteById(@Param("deleted") boolean deleted, @Param("id") String id);

}
