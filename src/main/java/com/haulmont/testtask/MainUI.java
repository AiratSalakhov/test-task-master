package com.haulmont.testtask;

import com.haulmont.testtask.domain.Customer;
import com.haulmont.testtask.service.CustomerService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.grid.GridConstants;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private CustomerService service = CustomerService.getInstance();
    //private Grid<Customer> grid = new Grid<>(Customer.class);


    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        BeanItemContainer<Customer> beanItemContainer = new BeanItemContainer<Customer>(Customer.class, service.findAll());


        Grid grid = new Grid(beanItemContainer);
        grid.addSelectionListener(selectionEvent -> { // Java 8
            // Get selection from the selection model
            Object selected = ((Grid.SingleSelectionModel)
                    grid.getSelectionModel()).getSelectedRow();

            if (selected != null)
                Notification.show("Selected " +
                        grid.getContainerDataSource().getItem(selected)
                                .getItemProperty("name"));
            else
                Notification.show("Nothing selected");
        });

        layout.addComponent(new Label("Main UI"));
        layout.addComponent(grid);

        setContent(layout);
    }
}