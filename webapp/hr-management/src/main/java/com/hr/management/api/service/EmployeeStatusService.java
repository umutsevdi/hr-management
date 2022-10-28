package com.hr.management.api.service;

import com.hr.management.api.repository.EmployeeStatusRepository;
import com.hr.management.api.repository.entity.EmployeeStatus;
import com.hr.management.api.service.model.EmployeeStatusDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeStatusService {
    private EmployeeStatusRepository employeeStatusRepository;

    public EmployeeStatusDto findEmployeeStatusesByEmployeeId(Long id) {
        EmployeeStatus employeeStatus = employeeStatusRepository.findEmployeeStatusByEmployeeId(id);
        if (employeeStatus != null) {
            return new EmployeeStatusDto(employeeStatus);
        }
        return null;
    }

    public List<EmployeeStatusDto> findEmployeeStatusesByTeamId(Long teamId) {
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findEmployeeStatusByTeamId(teamId);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<EmployeeStatusDto> findEmployeeStatusesByEmployeeIdAndTeamId(Long employeeId, Long teamId) {
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findEmployeeStatusesByEmployeeIdAndTeamId(employeeId, teamId);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<EmployeeStatusDto> findEmployeeStatusesByEmployeeIdIn(List<Long> employeeIdList) {
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findEmployeeStatusesByEmployeeIdIn(employeeIdList);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<EmployeeStatusDto> findEmployeeStatusesByTeamIdIn(List<Long> teamIdList) {
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findEmployeeStatusesByTeamIdIn(teamIdList);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
