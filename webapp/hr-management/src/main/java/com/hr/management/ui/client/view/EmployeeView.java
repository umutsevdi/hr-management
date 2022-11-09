package com.hr.management.ui.client.view;

import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.EmployeeStatusDto;
import com.hr.management.api.service.model.EmployeeStatusPastDto;
import com.hr.management.api.service.model.TeamDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeView extends EmployeeDto {
    private TeamDto team;
    private List<EmployeeStatusPastDto> pastEmployeeStatus;
    private EmployeeStatusDto employeeStatus;

    public EmployeeView() {
        super();
    }

    public EmployeeView(EmployeeDto employeeDto, TeamDto team, List<EmployeeStatusPastDto> pastEmployeeStatus,
                        EmployeeStatusDto employeeStatus) {
        super(
                employeeDto.getId(),
                employeeDto.getCreatedDate(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getDateOfBirth(),
                employeeDto.getGender(),
                employeeDto.getEmail(),
                employeeDto.getPhoneNumber(),
                employeeDto.getEducation(),
                employeeDto.getExperience(),
                employeeDto.getCv(),
                employeeDto.getProfile()
        );
        this.team = team;
        this.pastEmployeeStatus = pastEmployeeStatus;
        this.employeeStatus = employeeStatus;
    }

    public EmployeeView(EmployeeDto employeeDto, TeamDto team, EmployeeStatusDto employeeStatus) {
        this(employeeDto, team, null, employeeStatus);
    }

    public EmployeeView(EmployeeDto employeeDto, TeamDto team) {
        this(employeeDto, team, null, null);
    }

    public String getTitle() {
        return getEmployeeStatus().getTitle();
    }

    public String getTeamName() {
        return getTeam().getName();
    }

}
