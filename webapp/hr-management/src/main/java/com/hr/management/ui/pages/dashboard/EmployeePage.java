package com.hr.management.ui.pages.dashboard;

import com.hr.management.ui.client.view.EmployeeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EmployeePage extends VerticalLayout {

    EmployeeView employeeView;
    public EmployeePage(EmployeeView employeeView) {
        this.employeeView = employeeView;
        if (employeeView == null) {
            UI.getCurrent().navigate("/");
        }
        assert employeeView != null;
        H1 title = new H1(employeeView.getFirstName() + " " + employeeView.getLastName());
        add(title);
    }

}