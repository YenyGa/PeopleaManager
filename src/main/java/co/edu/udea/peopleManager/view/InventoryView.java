package co.edu.udea.peopleManager.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;

import co.edu.udea.peopleManager.business.ProductBusiness;
import co.edu.udea.peopleManager.model.Product;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import javax.annotation.PostConstruct;

@SpringView(name = InventoryView.VIEW_NAME)
public class InventoryView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5649888709595014944L;

	public static final String VIEW_NAME = "inventory";

    Grid<Product> grid;

    List<Product> productList;
    
    @Autowired
    ProductBusiness productBusiness;

    @PostConstruct
    void init() { 	
    	productList = productBusiness.findAll();
    	
    	grid = new Grid<>();
        grid.setItems(productList);
        grid.addColumn(Product::getId).setCaption("Codigo de barras").setExpandRatio(1);;
        grid.addColumn(Product::getName).setCaption("Nombre").setExpandRatio(1);;
        grid.addColumn(Product::getDescription).setCaption("Descripcion").setWidth(200);
        grid.addColumn(Product::getAmount).setCaption("Cantidad").setWidth(100);
        grid.addColumn(Product::getPrice).setCaption("Precio").setWidth(100);

        // Render a button that deletes the data row (item)
        
        grid.addColumn(person -> "Editar",
                new ButtonRenderer(clickEvent -> {
                	Product product = (Product)clickEvent.getItem();
                	UI.getCurrent().getNavigator().navigateTo(UpdateProductView.VIEW_NAME + "/" + product.getId());
              })).setWidth(90);
        
        grid.addColumn(person -> "Borrar",
              new ButtonRenderer(clickEvent -> {
            	  Product product = (Product)clickEvent.getItem();
            	  productBusiness.deleteProduct(product.getId());
            	  productList.remove(product);
                  grid.setItems(productList);
            })).setWidth(90);
        
        grid.setSizeFull();

        VerticalLayout fields = new VerticalLayout(grid);
        fields.setCaption("Inventario");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeFull();
        fields.setWidth("1200px");
        
        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();
        uiLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);

        addComponent(uiLayout);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}
