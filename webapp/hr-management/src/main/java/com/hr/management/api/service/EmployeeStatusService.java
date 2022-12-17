package com.hr.management.api.service;

import com.hr.management.api.repository.EmployeeRepository;
import com.hr.management.api.repository.EmployeeStatusPastRepository;
import com.hr.management.api.repository.EmployeeStatusRepository;
import com.hr.management.api.repository.entity.EmployeeStatus;
import com.hr.management.api.repository.entity.EmployeeStatusPast;
import com.hr.management.api.service.model.EmployeeStatusDto;
import com.hr.management.api.service.model.EmployeeStatusPastDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeStatusService {
    private EmployeeStatusRepository employeeStatusRepository;
    private EmployeeStatusPastRepository employeeStatusPastRepository;
    private final EmployeeRepository employeeRepository;

    public List<EmployeeStatusDto> findAll() {
        return employeeStatusRepository.findAll().stream().map(EmployeeStatusDto::new).collect(Collectors.toList());
    }

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


    public List<EmployeeStatusPastDto> findEmployeeStatusPastsByEmployeeId(Long id) {
        List<EmployeeStatusPast> employeeStatus = employeeStatusPastRepository.findEmployeeStatusPastsByEmployeeId(id);
        if (CollectionUtils.isEmpty(employeeStatus)) {
            return Collections.emptyList();
        }
        return employeeStatus.stream().map(EmployeeStatusPastDto::new).collect(Collectors.toList());
    }

    public List<EmployeeStatusPastDto> findEmployeeStatusPastsByTeamId(Long teamId) {
        List<EmployeeStatusPast> employeeStatusList = employeeStatusPastRepository.findEmployeeStatusPastByTeamId(teamId);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusPastDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<EmployeeStatusPastDto> findEmployeeStatusPastsByEmployeeIdAndTeamId(Long employeeId, Long teamId) {
        List<EmployeeStatusPast> employeeStatusList = employeeStatusPastRepository.findEmployeeStatusPastsByEmployeeIdAndTeamId(employeeId, teamId);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusPastDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<EmployeeStatusPastDto> findEmployeeStatusPastByEmployeeIdAndYear(Long employeeId, Integer year) {
        List<EmployeeStatusPast> employeeStatusList = employeeStatusPastRepository.findEmployeeStatusPastByEmployeeIdAndYear(employeeId, year);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusPastDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<EmployeeStatusPastDto> findEmployeeStatusPastByTeamIdAndYear(Long teamId, Integer year) {
        List<EmployeeStatusPast> employeeStatusList = employeeStatusPastRepository.findEmployeeStatusPastByTeamIdAndYear(teamId, year);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusPastDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<EmployeeStatusPastDto> findEmployeeStatusPastsByEmployeeIdIn(List<Long> employeeIdList) {
        List<EmployeeStatusPastDto> employeeStatusPastList = new LinkedList<>();
        for (Long id : employeeIdList) {
            List<EmployeeStatusPast> employeeStatus = employeeStatusPastRepository.findEmployeeStatusPastsByEmployeeId(id);
            if (!CollectionUtils.isEmpty(employeeStatus)) {
                employeeStatusPastList.addAll(employeeStatus.stream()
                        .map(EmployeeStatusPastDto::new)
                        .collect(Collectors.toList()));
            }
        }
        return employeeStatusPastList;
    }

    public List<EmployeeStatusPastDto> findEmployeeStatusPastsByTeamIdIn(List<Long> teamIdList) {
        List<EmployeeStatusPast> employeeStatusList = employeeStatusPastRepository.findEmployeeStatusPastsByTeamIdIn(teamIdList);
        if (!CollectionUtils.isEmpty(employeeStatusList)) {
            return employeeStatusList.stream().map(EmployeeStatusPastDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public boolean create(EmployeeStatusDto employeeStatusDto) {
        EmployeeStatus employeeStatus = new EmployeeStatus(employeeStatusDto);
        if (employeeStatus.getId() == null || !employeeStatusRepository.existsById(employeeStatus.getId())) {
            employeeStatusRepository.save(employeeStatus);
            return true;
        }
        return false;
    }

    public boolean update(EmployeeStatusDto employeeStatusDto) {
        EmployeeStatus employeeStatus = new EmployeeStatus(employeeStatusDto);
        if (employeeStatusRepository.existsById(employeeStatus.getId())) {
            employeeStatusRepository.save(employeeStatus);
            return true;
        }
        return false;
    }

    public boolean create(EmployeeStatusPastDto employeeStatusDto) {
        EmployeeStatusPast employeeStatus = new EmployeeStatusPast(employeeStatusDto);
        if (employeeStatus.getId() == null || !employeeStatusPastRepository.existsById(employeeStatus.getId())) {
            employeeStatusPastRepository.save(employeeStatus);
            return true;
        }
        return false;
    }

    public boolean update(EmployeeStatusPastDto employeeStatusDto) {
        EmployeeStatusPast employeeStatus = new EmployeeStatusPast(employeeStatusDto);
        if (employeeStatusPastRepository.existsById(employeeStatus.getId())) {
            employeeStatusPastRepository.save(employeeStatus);
            return true;
        }
        return false;
    }

    public EmployeeStatusPastDto saveToPast(EmployeeStatus employeeStatus, int workDaysLate, int workDaysOvertime) {
        if (employeeStatusRepository.existsById(employeeStatus.getId())) {
            EmployeeStatusPast employeeStatusPast = new EmployeeStatusPast(
                    null,
                    employeeStatus.getEmployeeId(),
                    employeeStatus.getTeamId(),
                    employeeStatus.getWorkingHour(),
                    employeeStatus.getCompletedSprints(),
                    employeeStatus.getAwaitingTasks(),
                    employeeStatus.getCompletedTasks(),
                    employeeStatus.getDelayedTasks(),
                    employeeStatus.getUnfinishedTasks(),
                    employeeStatus.getAverageTeamScore(), employeeStatus.getMonthlySalary(),
                    LocalDate.now().getYear(),
                    workDaysLate, workDaysOvertime
            );
            employeeStatusPast = employeeStatusPastRepository.save(employeeStatusPast);
            employeeStatusRepository.deleteById(employeeStatus.getId());
            return new EmployeeStatusPastDto(employeeStatusPast);
        }
        return null;
    }
}
