package com.hr.management.ui.client.view;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ExperienceData extends VerticalLayout {
    public ExperienceData(EmployeeView employeeView) {
        Avatar avatar = new Avatar(employeeView.getTeam().getProfile());
        H3 teamName = new H3(employeeView.getTeam().getName());
        Paragraph title = new Paragraph(employeeView.getEmployeeStatus().getTitle());
        Paragraph workingHour = new Paragraph("Working Hours:" + employeeView.getEmployeeStatus().getWorkingHour());
        Paragraph completedSprints = new Paragraph("Completed Sprints:" + employeeView.getEmployeeStatus().getCompletedSprints());
        Paragraph awaitingTasks = new Paragraph("Awaiting Tasks:" + employeeView.getEmployeeStatus().getAwaitingTasks());
        Paragraph completedTasks = new Paragraph("Completed Tasks:" + employeeView.getEmployeeStatus().getCompletedTasks());
        Paragraph delayedTasks = new Paragraph("Delayed Tasks:" + employeeView.getEmployeeStatus().getDelayedTasks());
        Paragraph unfinishedTasks = new Paragraph("Unfinished Tasks:" + employeeView.getEmployeeStatus().getUnfinishedTasks());
        Paragraph averageTeamScore = new Paragraph("Team Score" + employeeView.getEmployeeStatus().getAverageTeamScore());
        Paragraph monthlySalary = new Paragraph("Monthly Salary" + employeeView.getEmployeeStatus().getMonthlySalary());

        add(
                new HorizontalLayout(avatar, teamName, title), workingHour, completedSprints, awaitingTasks, completedTasks, delayedTasks, unfinishedTasks, averageTeamScore, monthlySalary
        );
    }
}
