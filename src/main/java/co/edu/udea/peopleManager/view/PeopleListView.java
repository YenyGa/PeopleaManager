package co.edu.udea.peopleManager.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;

import co.edu.udea.peopleManager.business.PersonBusiness;
import co.edu.udea.peopleManager.model.Person;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import javax.annotation.PostConstruct;

@SpringView(name = PeopleListView.VIEW_NAME)
public class PeopleListView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5649888709595014944L;

	public static final String VIEW_NAME = "peopleList";

    Grid<Person> grid;

    List<Person> productList;
    
    @Autowired
    PersonBusiness personBusiness;

    @PostConstruct
    void init() { 	
    	productList = personBusiness.findAll();
    	
    	grid = new Grid<>();
        grid.setItems(productList);
        grid.addColumn(Person::getId).setCaption("Cédula").setExpandRatio(1);;
        grid.addColumn(Person::getName).setCaption("Nombre").setExpandRatio(1);;
        grid.addColumn(Person::getPhone).setCaption("Teléfono");
        grid.addColumn(Person::getEmail).setCaption("Correo");
        grid.addColumn(Person::getAddress).setCaption("Dirección");
        grid.addColumn(Person::getRegisterDate).setCaption("Fecha registro");
        
        grid.addColumn(person -> "Borrar",
              new ButtonRenderer(clickEvent -> {
            	  Person person = (Person)clickEvent.getItem();
            	  personBusiness.deletePerson(person.getId());
            	  productList.remove(person);
                  grid.setItems(productList);
            })).setWidth(90);
        
        grid.setSizeFull();

        VerticalLayout fields = new VerticalLayout(grid);
        fields.setCaption("Listado de personas registradas");
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
