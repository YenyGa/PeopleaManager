package co.edu.udea.peopleManager.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.peopleManager.dao.ProductDao;
import co.edu.udea.peopleManager.model.Product;

@Component
public class AplicationSetUp {

	@Autowired
	ProductDao clientDao;

	@PostConstruct
	public void setUp() {
		clientDao.save(new Product("12345", "Camiseta mujer Wonder Woman", "Color rojo, Talla XS, Elaborada en algodon", 5, 30000));
		clientDao.save(new Product("67891", "Camiseta mujer Batman", "Color negro, Talla XS, Elaborada en algodon", 5, 30000));
		clientDao.save(new Product("67892", "Camiseta hombre Batman", "Color negro, Talla M, Elaborada en algodon", 5, 30000));
		clientDao.save(new Product("98765", "Mug Agents of Shield", "Cambia de color segun la temperatura del liquido", 10, 40000));
	}
}
