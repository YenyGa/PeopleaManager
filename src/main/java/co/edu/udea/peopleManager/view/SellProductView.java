package co.edu.udea.peopleManager.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import co.edu.udea.peopleManager.business.ProductBusiness;
import co.edu.udea.peopleManager.model.Product;
import co.edu.udea.peopleManager.util.Utilities;
import co.edu.udea.peopleManager.util.WebException;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

@SpringView(name = SellProductView.VIEW_NAME)
public class SellProductView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6743731430986406247L;

	public static final String VIEW_NAME = "";

    TextField txtId;
    TextField txtRequestedAmount;
    Button btnRegister;
    Button btnAdd;
    Label label;
    Label lblTotal;
    Label lblTotalPrice;
    Grid<Product> grid;
    
    List<Product> productList;
    int totalPrice;

    @Autowired
    ProductBusiness productBusiness;

    @PostConstruct
    void init() {
    	txtId = new TextField("Código de barras:");
        txtId.setWidth("300px");
        txtId.setRequiredIndicatorVisible(true);
        txtId.setId("txtIdProduct");

    	txtRequestedAmount = new TextField("Cantidad:");
    	txtRequestedAmount.setWidth("300px");
    	txtRequestedAmount.setRequiredIndicatorVisible(true);
    	txtRequestedAmount.setId("txtRequestedAmount");
    	txtRequestedAmount.setValue("1");
        
        btnRegister = new Button("Registrar Venta", this::registerButtonClick);
        btnRegister.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnRegister.setClickShortcut(KeyCode.ENTER);
        btnRegister.setId("btnLogin");

        btnAdd = new NativeButton("Agregar", this::addButtonClick);
        btnAdd.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnAdd.setId("btnAdd");
        
        lblTotal = new Label();
        lblTotal.setId("lblTotal");
        lblTotal.setValue("Total:");
        
        lblTotalPrice = new Label();
        lblTotalPrice.setId("lblTotalPrice");
        lblTotalPrice.setValue("0");
        
        totalPrice = 0;
        
        label = new Label();
        label.setId("label");
        label.setVisible(false);
    	label.setCaptionAsHtml(true);
    	label.setValue("");
        
    	productList = new ArrayList<>();

        grid = new Grid<>();
        grid.setItems(productList);
        grid.addColumn(Product::getId).setCaption("Codigo de barras");
        grid.addColumn(Product::getName).setCaption("Nombre");
        grid.addColumn(Product::getDescription).setCaption("Descripcion");
        grid.addColumn(Product::getAmount).setCaption("Cantidad");
        grid.addColumn(Product::getPrice).setCaption("Precio unitario");
        
        // Render a button that deletes the data row (item)
        grid.addColumn(person -> "Borrar",
              new ButtonRenderer(clickEvent -> {
            	  Product product = (Product)clickEvent.getItem();
            	  productList.remove(product);
            	  //productList.remove(clickEvent.getItem());
                  grid.setItems(productList);
                  totalPrice = totalPrice - (product.getPrice() * product.getAmount());
                  lblTotalPrice.setValue(String.valueOf(totalPrice));
            }));
        
        grid.setSizeFull();
        
        HorizontalLayout horizontalFields = new HorizontalLayout(txtId, txtRequestedAmount, btnAdd);
        horizontalFields.setCaption("Nueva venta");
        horizontalFields.setComponentAlignment(btnAdd, Alignment.BOTTOM_LEFT);
        
        HorizontalLayout horizontalFields2 = new HorizontalLayout(lblTotal, lblTotalPrice);
        grid.setHeight("300px");
        VerticalLayout uiLayout = new VerticalLayout(horizontalFields, grid, horizontalFields2, btnRegister, label);
        uiLayout.setComponentAlignment(horizontalFields, Alignment.MIDDLE_LEFT);
        uiLayout.setComponentAlignment(btnRegister, Alignment.TOP_CENTER);
        uiLayout.setComponentAlignment(horizontalFields2, Alignment.MIDDLE_RIGHT);
        uiLayout.setComponentAlignment(label, Alignment.TOP_CENTER);
        uiLayout.setSpacing(true);

        addComponent(uiLayout);
    }

    private void addButtonClick(Button.ClickEvent p) {
    	label.setVisible(false);
    	
    	if (txtId.isEmpty() || txtRequestedAmount.isEmpty() || txtRequestedAmount.getValue().equals("0")) {
    		label.setCaption("<label style='color: red'>Ingrese los datos faltantes</label>");
            label.setVisible(true);
    	} else {
    		
			try {
				if(Utilities.isNumeric(txtRequestedAmount.getValue())){
					
					Product selledProduct = productBusiness.findById(txtId.getValue());
					
					if(selledProduct.getAmount() != 0){
						selledProduct.setAmount(Integer.parseInt(txtRequestedAmount.getValue()));
						
						if(!checkDuplicatedId(selledProduct.getId(), selledProduct.getAmount())){
					    	productList.add(selledProduct);
						}

				    	grid.setItems(productList);
				    	totalPrice = totalPrice + (selledProduct.getPrice() * selledProduct.getAmount());
				    	lblTotalPrice.setValue(String.valueOf(totalPrice));
				    	
					}else{
						label.setCaption("<label style='color: red'>No hay suficiente stock de ese producto</label>");
			    		label.setVisible(true);
					}
			    	
				}else{
					label.setCaption("<label style='color: red'>La cantidad debe ser numerica</label>");
		    		label.setVisible(true);
				}
		    	
			} catch (WebException webEx) {
                System.out.println(webEx.getTechnicalMessage());
                label.setCaption("<label style='color: red'>"+webEx.getUserMessage()+"</label>");
                label.setVisible(true);
			}
			
    	}
    }

    public boolean checkDuplicatedId(String id, int amount){
    	for(int i=0; i<productList.size();i++){

    		if(id == productList.get(i).getId()){
    			productList.get(i).setAmount(productList.get(i).getAmount()+amount);
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    public void registerButtonClick(Button.ClickEvent e) {
    	try {
    		if(!productList.isEmpty()){
        		label.setVisible(false);
    			productBusiness.sellProducts(productList);
    			restartObjects();
    			label.setCaption("<label style='color: green'>Venta registrada con éxito</label>");
                label.setVisible(true);
    		}
            
		} catch (WebException webEx) {
            System.out.println(webEx.getTechnicalMessage());
            label.setCaption("<label style='color: red'>"+webEx.getUserMessage()+"</label>");
            label.setVisible(true);
		}
    }
    
    public void restartObjects(){
		productList.clear();
		grid.setItems(productList);
        lblTotalPrice.setValue("0");
        totalPrice = 0;
        txtId.setValue("");
        txtRequestedAmount.setValue("1");
    }

}
