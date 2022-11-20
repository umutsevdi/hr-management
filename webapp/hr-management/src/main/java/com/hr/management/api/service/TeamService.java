package com.hr.management.api.service;

import com.hr.management.api.repository.EmployeeRepository;
import com.hr.management.api.repository.EmployeeStatusRepository;
import com.hr.management.api.repository.TeamRepository;
import com.hr.management.api.repository.entity.EmployeeStatus;
import com.hr.management.api.repository.entity.Team;
import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.TeamDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamService {
    private EmployeeRepository employeeRepository;
    private EmployeeStatusRepository employeeStatusRepository;
    private TeamRepository teamRepository;

    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream().map(TeamDto::new).collect(Collectors.toList());
    }

    public TeamDto findById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.map(TeamDto::new).orElse(null);
        }
        return null;
    }

    public TeamDto findByName(String teamName) {
        Team team = teamRepository.findTeamByName(teamName);
        if (team == null) {
            return null;
        }
        return new TeamDto(team);
    }

    public TeamDto findByBossId(Long bossId) {
        Team team = teamRepository.findTeamByBossId(bossId);
        if (team == null) {
            return null;
        }
        return new TeamDto(team);
    }

    public List<TeamDto> findByRemote(Boolean isRemote) {
        List<Team> teamList = teamRepository.findAll();
        teamList.removeIf(i -> i.isRemote() != isRemote);

        if (CollectionUtils.isEmpty(teamList)) {
            return Collections.emptyList();
        }
        return teamList.stream().map(TeamDto::new).collect(Collectors.toList());
    }

    public TeamDto getTeam(EmployeeDto employee) {
        EmployeeStatus employeeStatus = employeeStatusRepository.findEmployeeStatusByEmployeeId(employee.getId());
        if (employeeStatus != null) {
            Optional<Team> team = teamRepository.findById(employeeStatus.getTeamId());
            return team.map(TeamDto::new).orElse(null);
        }
        return null;
    }
}
