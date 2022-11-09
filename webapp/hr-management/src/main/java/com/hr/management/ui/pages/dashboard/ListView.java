package com.hr.management.ui.pages.dashboard;


import com.hr.management.ui.client.EmployeeClient;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.components.EmployeeForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

@Route(value = "")
@PageTitle("Contacts | Vaadin CRM")
public class ListView extends VerticalLayout {
    EmployeeClient employeeClient;
    Grid<EmployeeView> grid = new Grid<>(EmployeeView.class);
    TextField filterText = new TextField();
    EmployeeForm form;

    public ListView(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new EmployeeForm(employeeClient.getTeamService().findAll());
        form.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "email", "teamName", "title");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList(e.getValue()));

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addContact());


        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editContact(EmployeeView employeeView) {
        if (employeeView == null) {
            closeEditor();
        } else {
            form.fillFieldsWith(employeeView);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.resetFields();
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new EmployeeView());
    }

    private void updateList() {
        grid.setItems(employeeClient.getEmployeesForView());
    }

    private void updateList(String query) {
        grid.setItems(employeeClient.filterBy(employeeClient.getEmployeesForView(), parseQuery(query)));
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> parsedQuery = new HashMap<>();
        for (String entry : query.split(",")) {
            String[] singleQuery = entry.split(":");
            if (singleQuery.length == 2) {
                singleQuery[0] = singleQuery[0].strip();
                singleQuery[1] = singleQuery[1].strip();
                parsedQuery.put(singleQuery[0], singleQuery[1]);
            }
        }
        return parsedQuery;
    }

}