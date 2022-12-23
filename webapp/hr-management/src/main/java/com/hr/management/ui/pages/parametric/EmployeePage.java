package com.hr.management.ui.pages.parametric;

import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.ui.client.PageClient;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.components.EmployeeForm;
import com.hr.management.ui.pages.BaseLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.rmi.NoSuchObjectException;

@Route(value = "/employees")
@PageTitle("Employee | Human Resources")
@Getter
@Log4j2
public class EmployeePage extends BaseLayout implements HasUrlParameter<Long> {
    Long employeeId;
    EmployeeView employee;

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long value) {
        if (value != null) {
            employeeId = value;
        } else {
            UI.getCurrent().navigate("");
        }
    }

    public EmployeePage(PageClient pageClient) {
        super(pageClient);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        try {
            EmployeeDto employeeDto = getClient().getEmployeeService().findEmployeeById(employeeId);
            employee = new EmployeeView(
                    employeeDto,
                    getClient().getTeamService().getTeam(employeeDto),
                    getClient().getEmployeeStatusService().findEmployeeStatusesByEmployeeId(employeeDto.getId())
            );
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            UI.getCurrent().navigate("/employees");
        }
        if (employee == null) {
            UI.getCurrent().navigate("/employees");
        }
        EmployeeForm employeeForm = new EmployeeForm(getClient().getTeams(), false);
        employeeForm.fillFieldsWith(employee);
        add(employeeForm);
    }

}