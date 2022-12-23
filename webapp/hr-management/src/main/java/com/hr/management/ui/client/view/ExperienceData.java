package com.hr.management.ui.client.view;

import com.hr.management.api.service.model.EmployeeStatusDto;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class ExperienceData extends VerticalLayout {
    EmployeeStatusDto employeeStatusDto;
    TextField title = new TextField("Title");
    NumberField workingHour = new NumberField("Working Hour");
    NumberField completedSprints = new NumberField("Completed Sprints");
    NumberField awaitingTasks = new NumberField("Awaiting Tasks");
    NumberField completedTasks = new NumberField("Completed Tasks");
    NumberField delayedTasks = new NumberField("Delayed Tasks");
    NumberField unfinishedTasks = new NumberField("Unfinished Tasks");
    NumberField averageTeamScore = new NumberField("Team Score");
    NumberField monthlySalary = new NumberField("Monthly Salary");

    public ExperienceData(EmployeeView employeeView) {
        addClassName("contact-form");
        title.setValue(employeeView.getEmployeeStatus().getTitle());
        workingHour.setValue(Double.valueOf(employeeView.getEmployeeStatus().getWorkingHour()));
        completedSprints.setValue(Double.valueOf(employeeView.getEmployeeStatus().getCompletedSprints()));
        awaitingTasks.setValue(Double.valueOf(employeeView.getEmployeeStatus().getAwaitingTasks()));
        completedTasks.setValue(Double.valueOf(employeeView.getEmployeeStatus().getCompletedTasks()));
        delayedTasks.setValue(Double.valueOf(employeeView.getEmployeeStatus().getDelayedTasks()));
        unfinishedTasks.setValue(Double.valueOf(employeeView.getEmployeeStatus().getUnfinishedTasks()));
        averageTeamScore.setValue(Double.valueOf(employeeView.getEmployeeStatus().getAverageTeamScore()));
        monthlySalary.setValue(employeeView.getEmployeeStatus().getMonthlySalary());

        VerticalLayout verticalLayout = new VerticalLayout(title, workingHour, completedSprints, awaitingTasks, completedTasks, delayedTasks, unfinishedTasks, averageTeamScore, monthlySalary);
        verticalLayout.setSizeFull();
        add(verticalLayout);

        employeeStatusDto = employeeView.getEmployeeStatus();
    }

    public EmployeeStatusDto toStatus() {
        return new EmployeeStatusDto(
                employeeStatusDto.getEmployeeId(),
                employeeStatusDto.getTeamId(),
                workingHour.getValue().longValue(),
                completedSprints.getValue().intValue(),
                awaitingTasks.getValue().intValue(),
                completedTasks.getValue().intValue(),
                delayedTasks.getValue().intValue(),
                unfinishedTasks.getValue().intValue(),
                averageTeamScore.getValue().floatValue(),
                monthlySalary.getValue(),
                title.getValue()
        );
    }
}
