package com.haulmont.testtask;

import com.haulmont.testtask.domain.Customer;
import com.haulmont.testtask.service.CustomerService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private CustomerService service = CustomerService.getInstance();

    @Override
    protected void init(VaadinRequest request) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:testdb", "SA", "sa");
        }
        catch (SQLException e) {
            throw new RuntimeException("Database connection error: " + e.getMessage());
        }
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table if not exists CLIENTS (id INTEGER GENERATED BY DEFAULT" +
                    " AS IDENTITY(START WITH 1) PRIMARY KEY, firstname varchar(50), lastname varchar(50), " +
                    "middlename varchar(50), phonenumber varchar(15)); " +
                    "insert into CLIENTS (firstname, lastname, middlename, phonenumber) values ('Vasiliy','Pupkov','Ivanovich', '1111-1111'); checkpoint;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        VerticalLayout layout = new VerticalLayout();

        BeanItemContainer<Customer> beanItemContainer =
                new BeanItemContainer<Customer>(Customer.class, service.findAll());


        Grid grid = new Grid(beanItemContainer);
        grid.addItemClickListener(event -> // Java 8
                Notification.show("Value: " +
                        beanItemContainer.getContainerProperty(event.getItemId(),
                                event.getPropertyId()).getValue().toString()));
/*        grid.addSelectionListener(selectionEvent -> { // Java 8
            // Get selection from the selection model
            Object selected = ((Grid.SingleSelectionModel)
                    grid.getSelectionModel()).getSelectedRow();

            if (selected != null)
                Notification.show("Selected " +
                        grid.getContainerDataSource().getItem(selected)
                                .getItemProperty("firstName"));
            else
                Notification.show("Nothing selected");
        });
*/
        layout.addComponent(new Label("Main UI"));
        layout.addComponent(grid);
        layout.setSizeFull();
        layout.setMargin(true);

        setContent(layout);
    }
}