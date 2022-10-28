package com.hr.management.api.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ComplexTeamDto extends TeamDto {
    List<EmployeeDto> employees;
    EmployeeDto boss;

    public ComplexTeamDto(TeamDto teamDto, EmployeeDto boss, List<EmployeeDto> employees) {
        super(
                teamDto.getId(),
                teamDto.getCreatedDate(),
                teamDto.getName(),
                teamDto.getBossId(),
                teamDto.getProfile(),
                teamDto.isRemote(),
                teamDto.getIpAddress()
        );
        this.employees = employees;
        this.boss = boss;
    }
}
