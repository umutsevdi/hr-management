package com.hr.management.api.service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComplexEmployeeDto extends EmployeeDto {
    private TeamDto team;
    private String title;

    private List<EmployeeStatusPastDto> pastEmployeeStatus;
    private EmployeeStatusDto employeeStatus;

    public ComplexEmployeeDto(EmployeeDto employeeDto, TeamDto team, String title, List<EmployeeStatusPastDto> pastEmployeeStatus,
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
        this.title = title;
        this.pastEmployeeStatus = pastEmployeeStatus;
        this.employeeStatus = employeeStatus;
    }

    public ComplexEmployeeDto(EmployeeDto employeeDto, TeamDto team, String title, EmployeeStatusDto employeeStatus) {
        this(employeeDto, team, title, null, employeeStatus);
    }

    public ComplexEmployeeDto(EmployeeDto employeeDto, TeamDto team, String title) {
        this(employeeDto, team, title, null, null);
    }
}
