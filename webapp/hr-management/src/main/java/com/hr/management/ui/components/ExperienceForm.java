package com.hr.management.ui.components;

import com.hr.management.api.service.model.EmployeeStatusDto;
import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.view.EmployeeView;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class ExperienceForm extends FormLayout implements FormComponent<EmployeeStatusDto> {
    EmployeeView employeeView;
    EmployeeStatusDto employeeStatusDto;
    private final Map<Long, TeamDto> teamMap;
    ComboBox<String> teamName = new ComboBox<>("Teams");

    Avatar teamAvatar = new Avatar();
    TextField title = new TextField("Title");
    NumberField workingHour = new NumberField("Working Hour");
    NumberField completedSprints = new NumberField("Completed Sprints");
    NumberField awaitingTasks = new NumberField("Awaiting Tasks");
    NumberField completedTasks = new NumberField("Completed Tasks");
    NumberField delayedTasks = new NumberField("Delayed Tasks");
    NumberField unfinishedTasks = new NumberField("Unfinished Tasks");
    NumberField averageTeamScore = new NumberField("Team Score");
    NumberField monthlySalary = new NumberField("Monthly Salary");

    public ExperienceForm(List<TeamDto> teams) {
        addClassName("contact-form");
        add(new HorizontalLayout(teamAvatar, teamName),
                title, workingHour, completedSprints, awaitingTasks, completedTasks, delayedTasks, unfinishedTasks, averageTeamScore, monthlySalary);

        teamMap = teams.stream().collect(Collectors.toMap(TeamDto::getId, Function.identity()));
        teamName.setItems(teamMap.values().stream().map(TeamDto::getName).collect(Collectors.toList()));

        teamName.addValueChangeListener(
                i -> {
                    if (employeeView != null) {
                        TeamDto team = null;
                        for (TeamDto t : teams) {
                            if (t.getName().equals(teamName.getValue())) {
                                team = t;
                                break;
                            }
                        }
                        assert team != null;
                        teamAvatar.setImage(team.getProfile());
                        teamAvatar.setName(team.getName());
                    }
                }
        );
    }

    public ExperienceForm resetFields() {
        title.clear();
        workingHour.clear();
        completedSprints.clear();
        awaitingTasks.clear();
        completedTasks.clear();
        delayedTasks.clear();
        unfinishedTasks.clear();
        awaitingTasks.clear();
        monthlySalary.clear();
        teamName.clear();
        teamAvatar.setImage(null);
        employeeStatusDto = null;
        employeeView = null;
        return this;
    }


    public EmployeeStatusDto readFields() {
        if (employeeView == null) {
            return null;
        }
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

    public TeamDto readTeamField() {
        if (employeeView == null) {
            return null;
        }
        return teamMap.values().stream()
                .filter(i -> i.getName().equals(teamName.getValue()))
                .findFirst()
                .orElse(teamMap.values().iterator().next());
    }

    public ExperienceForm fillFieldsWith(EmployeeView employeeView) {
        this.employeeView = employeeView;
        title.setValue(employeeView.getEmployeeStatus().getTitle());
        workingHour.setValue(Double.valueOf(employeeView.getEmployeeStatus().getWorkingHour()));
        completedSprints.setValue(Double.valueOf(employeeView.getEmployeeStatus().getCompletedSprints()));
        awaitingTasks.setValue(Double.valueOf(employeeView.getEmployeeStatus().getAwaitingTasks()));
        completedTasks.setValue(Double.valueOf(employeeView.getEmployeeStatus().getCompletedTasks()));
        delayedTasks.setValue(Double.valueOf(employeeView.getEmployeeStatus().getDelayedTasks()));
        unfinishedTasks.setValue(Double.valueOf(employeeView.getEmployeeStatus().getUnfinishedTasks()));
        averageTeamScore.setValue(Double.valueOf(employeeView.getEmployeeStatus().getAverageTeamScore()));
        monthlySalary.setValue(employeeView.getEmployeeStatus().getMonthlySalary());
        teamName.setValue(teamMap.get(employeeView.getEmployeeStatus().getTeamId()).getName());
        employeeStatusDto = employeeView.getEmployeeStatus();
        return this;
    }
}
