package co.edu.udea.peopleManager.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udea.peopleManager.dao.ProductDao;
import co.edu.udea.peopleManager.model.Product;
import co.edu.udea.peopleManager.util.WebException;

import java.util.List;

@Service
public class ProductBusiness {

    private ProductDao productDao;

    @Autowired
    public ProductBusiness(ProductDao clientDao) {
        this.productDao = clientDao;
    }

    public void checkDuplicateId(String id) throws WebException {
        Product product = productDao.findById(id);

        if (product != null) {
            WebException webEx = new WebException();
            webEx.setUserMessage("El producto ya se encuentra registrado");
            webEx.setTechnicalMessage("el producto retornado en productDao.findById(id) no es nulo");
            throw webEx;
        }
    }
    
    public void sellProducts(List<Product> productList) throws WebException{
    	for(int i=0; i<productList.size(); i++){
    		updateProductAmount(productList.get(i).getAmount(), productList.get(i).getId());
    	}
    }
    
    public int updateProductAmount(int recuestedAmount, String id) throws WebException{
    	int amount = checkProductStock(id, recuestedAmount) - recuestedAmount;
    	productDao.updateAmountById(amount, id);
    	
    	return amount;
    }
    
    public int checkProductStock(String id, int recuestedAmount) throws WebException{
    	Product product = productDao.findById(id);
    	
    	int productAmount = product.getAmount();
    	
    	if( productAmount < recuestedAmount){
    		WebException webEx = new WebException();
            webEx.setUserMessage("No hay stock suficiente");
            webEx.setTechnicalMessage("La condicion product.getAmount() < recuestedAmount es true");
            throw webEx;
    	}
    	return productAmount;
    }
    
    public Product findById(String id) throws WebException{
        Product product = productDao.findById(id);

        if (product == null) {
            WebException webEx = new WebException();
            webEx.setUserMessage("El producto no se encuentra registrado");
            webEx.setTechnicalMessage("el producto retornado en productDao.findById(id) es nulo");
            throw webEx;
        }
        return product;
    }
    
    public List<Product> findAll(){
    	return productDao.findAll();
    }
    
    public void saveProduct(Product client) {
        productDao.save(client);
    }
    
    public void updateProduct(String id, int amount, String name, String description, int price){
    	productDao.updateProduct(id, amount, name, description, price);
    }
    
    public void deleteProduct(String id){
    	productDao.deleteById(id);
    }
    
}
