package co.edu.udea.peopleManager.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class MainUI extends UI {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3051380633319672478L;
	@Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout uiLayout = new VerticalLayout();
        setSizeFull();
        setContent(uiLayout);

        final CssLayout navigationBar = new CssLayout();
        Label label = new Label("PEOPLE MANAGER");
        navigationBar.addComponent(createNavigationButton("Registrar persona", RegisterPersonView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Listar personas", PeopleListView.VIEW_NAME));
        uiLayout.addComponent(label);
        uiLayout.addComponent(navigationBar);
        uiLayout.setSizeFull();

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        uiLayout.addComponent(viewContainer);
        uiLayout.setMargin(false);
        uiLayout.setExpandRatio(viewContainer, 1.0f);
        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);

    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

}
