package co.edu.udea.peopleManager.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import co.edu.udea.peopleManager.business.PersonBusiness;
import co.edu.udea.peopleManager.model.Person;
import co.edu.udea.peopleManager.util.Utilities;
import co.edu.udea.peopleManager.util.WebException;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = RegisterPersonView.VIEW_NAME)
public class RegisterPersonView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3124403167539294072L;

	public static final String VIEW_NAME = "registerPerson";

    TextField txtId;
    TextField txtName;
    TextField txtAddress;
    TextField txtPhone;
    TextField txtEmail;
    Button btnRegister;
    Label label;

    @Autowired
    PersonBusiness productBusiness;

    @PostConstruct
    void init() {
        txtId = new TextField("Cédula");
        txtId.setWidth("300px");
        txtId.setRequiredIndicatorVisible(true);

        txtName = new TextField("Nombre:");
        txtName.setWidth("300px");
        txtName.setRequiredIndicatorVisible(true);

        txtAddress = new TextField("Dirección");
        txtAddress.setWidth("300px");
        txtAddress.setRequiredIndicatorVisible(true);
        
        txtPhone = new TextField("Teléfono:");
        txtPhone.setWidth("300px");
        txtPhone.setRequiredIndicatorVisible(true);
        txtPhone.setId("txtAmountProduct");

        txtEmail = new TextField("Correo electronico::");
        txtEmail.setWidth("300px");
        txtEmail.setRequiredIndicatorVisible(true);

        btnRegister = new Button("Registrar", this::registerButtonClick);
        btnRegister.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnRegister.setClickShortcut(KeyCode.ENTER);

        label = new Label();
        label.setId("label");
        label.setVisible(false);
    	label.setCaptionAsHtml(true);
    	label.setValue("");

        VerticalLayout fields = new VerticalLayout(txtId, txtAddress, txtName, txtPhone, txtEmail, btnRegister, label);
        fields.setCaption("Registrar Persona");
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

        if (txtId.isEmpty() || txtPhone.isEmpty() || txtAddress.isEmpty() || txtName.isEmpty() || txtEmail.isEmpty() 
        		|| txtEmail.getValue().equals("0")) {
            label.setCaption("<label style='color: red'>Ingrese los datos faltantes</label>");
            label.setVisible(true);

        } else {
            try {
            	
            	if(Utilities.isNumeric(txtPhone.getValue()) && Utilities.isEmail(txtEmail.getValue())){
            		String phone = txtPhone.getValue();
                    String name = txtName.getValue();
                    String id = txtId.getValue();
                    String address = txtAddress.getValue();
                    String email = txtEmail.getValue();

                    productBusiness.checkDuplicateId(id);

                    Person newPerson = new Person(id, name, address, phone, email);
                    productBusiness.savePerson(newPerson);

                    label.setCaption("<label style='color: green'>Persona registrada exitosamente</label>");
                    label.setVisible(true);
            	}else{	
					label.setCaption("<label style='color: red'>Telefono o correo no valido</label>");
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
