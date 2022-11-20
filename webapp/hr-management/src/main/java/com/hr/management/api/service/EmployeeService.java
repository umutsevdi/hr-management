package com.hr.management.api.service;

import com.hr.management.api.repository.EmployeeRepository;
import com.hr.management.api.repository.EmployeeStatusRepository;
import com.hr.management.api.repository.entity.Employee;
import com.hr.management.api.repository.entity.EmployeeStatus;
import com.hr.management.api.service.model.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log4j2
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeStatusRepository employeeStatusRepository;

    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    public EmployeeDto findEmployeeById(Long id) throws NoSuchObjectException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NoSuchObjectException(Employee.class.toString()));
        log.info("findByEmployeeId {} -> {}", id, employee);
        return new EmployeeDto(employee);
    }

    public EmployeeDto findEmployeeByEmail(String email) throws NoSuchObjectException {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null) {
            throw new NoSuchObjectException(Employee.class.toString());
        }
        return new EmployeeDto(employee);
    }

    public EmployeeDto findEmployeeByPhoneNumber(String phoneNumber) throws NoSuchObjectException {
        Employee employee = employeeRepository.findEmployeeByPhoneNumber(phoneNumber);
        if (employee == null) {
            throw new NoSuchObjectException(Employee.class.toString());
        }
        return new EmployeeDto(employee);

    }

    public List<EmployeeDto> findByTeamId(Long teamId) throws NoSuchObjectException {
        List<Employee> employeeList = employeeRepository.findEmployeesByIdIn(employeeStatusRepository
                .findEmployeeStatusByTeamId(teamId)
                .stream()
                .map(EmployeeStatus::getEmployeeId)
                .collect(Collectors.toList())
        );
        if (CollectionUtils.isEmpty(employeeList)) {
            throw new NoSuchObjectException(Employee.class.toString());
        }
        return employeeList
                .stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

}