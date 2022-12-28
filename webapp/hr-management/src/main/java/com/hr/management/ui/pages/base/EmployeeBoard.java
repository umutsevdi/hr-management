package com.hr.management.ui.pages.base;


import com.hr.management.ui.client.PageClient;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.components.CommonComponentUtil;
import com.hr.management.ui.components.EmployeeForm;
import com.hr.management.ui.pages.BaseLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Route(value = "")
@PageTitle("Dashboard | Human Resources")
@Getter
public class EmployeeBoard extends BaseLayout {
    Grid<EmployeeView> grid = new Grid<>(EmployeeView.class);
    TextField filterText = new TextField();
    EmployeeForm form;

    Paragraph affectedFields = new Paragraph();

    Button save;
    Button delete;

    public EmployeeBoard(PageClient pageClient) {
        super(pageClient);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        getDrawer().setSelectedIndex(0);
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        form = new EmployeeForm();
        Map.Entry<Button, Button> buttons = CommonComponentUtil.createButtonsLayout();
        save = buttons.getKey();
        delete = buttons.getValue();
        form.add(new HorizontalLayout(save, delete));
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

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(new ComponentRenderer<>(
                i -> new Anchor("/employees/" + i.getId(), i.getId().toString()))
        ).setHeader("Id");
        grid.addColumns("firstName", "lastName", "email");
        grid.addColumn(new ComponentRenderer<>(
                i -> new Anchor("/teams/" + i.getTeam().getId().toString(), i.getTeamName()))
        ).setHeader("Team");
        grid.addColumn("title");
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
        List<EmployeeView> employeeViewList = getClient().toView(getClient().getEmployeeService().findAll());
        affectedFields.setText(employeeViewList.size() + (employeeViewList.size() > 1 ? " employees found" : " employee found"));
        grid.setItems(employeeViewList);
    }

    private void updateList(String query) {
        List<EmployeeView> employeeViewList = getClient().
                filterBy(getClient().toView(getClient().getEmployeeService().findAll()), query);
        affectedFields.setText(employeeViewList.size() + (employeeViewList.size() > 1 ? " employees found" : " employee found"));
        grid.setItems(employeeViewList);
    }

}