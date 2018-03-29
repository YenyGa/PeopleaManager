package co.edu.udea.peopleManager.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import co.edu.udea.peopleManager.business.ProductBusiness;
import co.edu.udea.peopleManager.model.Product;
import co.edu.udea.peopleManager.util.Utilities;
import co.edu.udea.peopleManager.util.WebException;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = NewProductView.VIEW_NAME)
public class NewProductView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3124403167539294072L;

	public static final String VIEW_NAME = "newProduct";

    TextField txtId;
    TextField txtName;
    TextField txtDescription;
    TextField txtAmount;
    TextField txtPrice;
    Button btnRegister;
    Label label;

    @Autowired
    ProductBusiness productBusiness;

    @PostConstruct
    void init() {
        txtId = new TextField("Código de barras:");
        txtId.setWidth("300px");
        txtId.setRequiredIndicatorVisible(true);
        txtId.setId("txtIdProduct");

        txtName = new TextField("Nombre:");
        txtName.setWidth("300px");
        txtName.setRequiredIndicatorVisible(true);
        txtName.setId("txtNameProduct");

        txtDescription = new TextField("Descripción:");
        txtDescription.setWidth("300px");
        txtDescription.setRequiredIndicatorVisible(true);
        txtDescription.setId("txtDescriptionProduct");
        
        txtAmount = new TextField("Cantidad:");
        txtAmount.setWidth("300px");
        txtAmount.setRequiredIndicatorVisible(true);
        txtAmount.setId("txtAmountProduct");
        txtAmount.setValue("0");

        txtPrice = new TextField("Precio:");
        txtPrice.setWidth("300px");
        txtPrice.setRequiredIndicatorVisible(true);
        txtPrice.setId("txtPriceProduct");
        txtPrice.setValue("0");

        btnRegister = new Button("Registrar", this::registerButtonClick);
        btnRegister.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnRegister.setClickShortcut(KeyCode.ENTER);
        btnRegister.setId("btnRegister");

        label = new Label();
        label.setId("label");
        label.setVisible(false);
    	label.setCaptionAsHtml(true);
    	label.setValue("");

        VerticalLayout fields = new VerticalLayout(txtId, txtDescription, txtName, txtAmount, txtPrice, btnRegister, label);
        fields.setCaption("Registrar Nuevo Producto");
        fields.setComponentAlignment(btnRegister, Alignment.TOP_CENTER);
        fields.setComponentAlignment(label, Alignment.TOP_CENTER);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

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

    public void registerButtonClick(Button.ClickEvent e) {
    	label.setVisible(false);

        if (txtId.isEmpty() || txtAmount.isEmpty() || txtDescription.isEmpty() || txtName.isEmpty() || txtPrice.isEmpty() 
        		|| txtPrice.getValue().equals("0")) {
            label.setCaption("<label style='color: red'>Ingrese los datos faltantes</label>");
            label.setVisible(true);

        } else {
            try {
            	
            	if(Utilities.isNumeric(txtAmount.getValue()) && Utilities.isNumeric(txtPrice.getValue())){
            		String amount = txtAmount.getValue();
                    String name = txtName.getValue();
                    String id = txtId.getValue();
                    String description = txtDescription.getValue();
                    String price = txtPrice.getValue();

                    productBusiness.checkDuplicateId(id);

                    Product newProduct = new Product(id, name, description, Integer.parseInt(amount), Integer.parseInt(price));
                    productBusiness.saveProduct(newProduct);

                    label.setCaption("<label style='color: green'>Producto registrado exitosamente</label>");
                    label.setVisible(true);
            	}else{	
					label.setCaption("<label style='color: red'>La cantidad y el precio deben ser numericos</label>");
		    		label.setVisible(true);
				}

            } catch (WebException webEx) {
                System.out.println(webEx.getTechnicalMessage());
                label.setCaption("<label style='color: red'>"+webEx.getUserMessage()+"</label>");
                label.setVisible(true);
            }
        }
        
    }
}
