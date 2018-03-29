package com.todo1.hulkStore.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.edu.udea.peopleManager.business.ProductBusiness;
import co.edu.udea.peopleManager.dao.ProductDao;
import co.edu.udea.peopleManager.model.Product;
import co.edu.udea.peopleManager.util.WebException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductBusinessTest {

	private ProductBusiness productBusiness;
    
    @Mock
    private ProductDao productDao;
    
    @Before
    public void init() {
        this.productBusiness = new ProductBusiness(productDao);
    }
    
    @Test(expected = WebException.class)
	public void ifIdExistsThrowsError() throws WebException {
    	Product product = new Product("99999", "Camiseta hombre Capitan America", "Color azul, Talla M, Elaborada en algodon", 7, 35000);

    	when(productDao.findById(product.getId())).thenReturn(product);
    	productBusiness.checkDuplicateId(product.getId());
	}

    @Test(expected = WebException.class)
	public void ifProductStockIsZeroThrowsError() throws WebException {
		Product product = new Product("99999", "Camiseta hombre Capitan America", "Color azul, Talla M, Elaborada en algodon", 0, 35000);
		
		when(productDao.findById(product.getId())).thenReturn(product);
		productBusiness.checkProductStock(product.getId(), 5);
	}
	
    @Test(expected = WebException.class)
	public void ifProductStockIsLowerThanRecuestedThrowsError() throws WebException {
    	Product product = new Product("99999", "Camiseta hombre Capitan America", "Color azul, Talla M, Elaborada en algodon", 1, 35000);
		
		when(productDao.findById(product.getId())).thenReturn(product);
		productBusiness.checkProductStock(product.getId(), 2);
	}
    
    @Test
	public void ifProductStockIsHiguerThanRecuestedReturnsAmount() throws WebException {
    	Product product = new Product("99999", "Camiseta hombre Capitan America", "Color azul, Talla M, Elaborada en algodon", 10, 35000);
	
		when(productDao.findById(product.getId())).thenReturn(product);
		int result = productBusiness.checkProductStock(product.getId(), 2);
		
		assertEquals(10, result);
	}
    
    @Test
   	public void whenAProductIsSellAmountMustBeUpdated() throws WebException {
    	Product product = new Product("99999", "Camiseta hombre Capitan America", "Color azul, Talla M, Elaborada en algodon", 10, 35000);
    	
		when(productDao.findById(product.getId())).thenReturn(product);
		int result = productBusiness.updateProductAmount(2, product.getId());
		
		assertEquals(8, result);
   	}

}
