package com.hr.management.ui.pages.dashboard;


import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.PageClient;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.components.EmployeeForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;

public class Dashboard extends VerticalLayout {
    App app;
    PageClient client;
    Grid<EmployeeView> grid = new Grid<>(EmployeeView.class);
    TextField filterText = new TextField();
    EmployeeForm form;

    Paragraph affectedFields = new Paragraph();

    public Dashboard(App app, List<TeamDto> teams) {
        this.app = app;
        this.client = app.getClient();
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm(teams);
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

    private void configureForm(List<TeamDto> teams) {
        form = new EmployeeForm(teams);
        form.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(new ComponentRenderer<>(
                i -> new Anchor("/employee/" + i.getId(), i.getId().toString()))).setHeader("Id");
        grid.addColumns("firstName", "lastName", "email", "teamName", "title");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList(e.getValue()));

        Button addContactButton = new Button("New Employee");
        addContactButton.addClickListener(click -> addContact());


        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton, affectedFields);
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
        List<EmployeeView> employeeViewList = client.toView(client.getEmployeeService().findAll());
        affectedFields.setText(employeeViewList.size() + (employeeViewList.size() > 1 ? " employees found" : " employee found"));
        grid.setItems(employeeViewList);
    }

    private void updateList(String query) {
        List<EmployeeView> employeeViewList = client.
                filterBy(client.toView(client.getEmployeeService().findAll()), query);
        affectedFields.setText(employeeViewList.size() + (employeeViewList.size() > 1 ? " employees found" : " employee found"));
        grid.setItems(employeeViewList);
    }


}