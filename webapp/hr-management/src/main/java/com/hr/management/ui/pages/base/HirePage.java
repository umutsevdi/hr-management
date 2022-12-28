package com.hr.management.ui.pages.base;

import com.hr.management.ui.client.PageClient;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.components.CommonComponentUtil;
import com.hr.management.ui.components.EmployeeForm;
import com.hr.management.ui.components.ExperienceForm;
import com.hr.management.ui.pages.BaseLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Route(value = "/employees/new")
@PageTitle("Hire")
@Getter
@Log4j2
public class HirePage extends BaseLayout {
    Long employeeId;
    EmployeeView employee;

    EmployeeForm employeeForm;

    ExperienceForm experienceData;
    Button save = new Button("Save");
    Button delete = new Button("Delete");

    public HirePage(PageClient pageClient) {
        super(pageClient);
        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        getDrawer().setSelectedIndex(this.getDrawer().getComponentCount() - 1);
        employeeForm = new EmployeeForm(false);
        employeeForm.resetFields();
        experienceData = new ExperienceForm(getClient().getTeams());
        experienceData.resetFields();
        Map.Entry<Button, Button> buttons = CommonComponentUtil.createButtonsLayout();
        save = buttons.getKey();
        delete = buttons.getValue();
        add(new HorizontalLayout(employeeForm, experienceData), new HorizontalLayout(buttons.getKey(), buttons.getValue()));
    }
}
