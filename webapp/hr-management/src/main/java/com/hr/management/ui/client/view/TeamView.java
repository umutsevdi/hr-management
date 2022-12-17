package com.hr.management.ui.client.view;

import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.TeamDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TeamView extends TeamDto {
    List<EmployeeDto> employees;
    EmployeeDto boss;

    public TeamView(TeamDto teamDto, EmployeeDto boss, List<EmployeeDto> employees) {
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
