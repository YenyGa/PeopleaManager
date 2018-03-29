package co.edu.udea.peopleManager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.peopleManager.model.Product;

public interface ProductDao extends CrudRepository<Product, String>{
	
	Product findById(String id);
	
	List<Product> findAll();
	
	List<Product> findByName(String name);
	
	List<Product> findByNameContaining(String name);
	
	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.amount = :amount where p.id = :id")
	int updateAmountById(@Param("amount") int amount, @Param("id") String id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.amount = :amount, p.name = :name, p.description = :description, p.price = :price where p.id = :id")
	int updateProduct(@Param("id") String id, @Param("amount") int amount, @Param("name") String name, 
			@Param("description") String description, @Param("price") int price);
	
	@Transactional
	void deleteById(String id);

}
