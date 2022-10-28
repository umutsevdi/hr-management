package com.hr.management.api.service;

import com.hr.management.api.repository.EmployeeRepository;
import com.hr.management.api.repository.EmployeeStatusRepository;
import com.hr.management.api.repository.entity.Employee;
import com.hr.management.api.repository.entity.EmployeeStatus;
import com.hr.management.api.service.model.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeStatusRepository employeeStatusRepository;

    EmployeeDto findEmployeeById(Long id) throws NoSuchObjectException {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            throw new NoSuchObjectException(Employee.class.toString());
        }
        return new EmployeeDto(employee);
    }

    EmployeeDto findEmployeeByEmail(String email) throws NoSuchObjectException {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null) {
            throw new NoSuchObjectException(Employee.class.toString());
        }
        return new EmployeeDto(employee);
    }

    EmployeeDto findEmployeeByPhoneNumber(String phoneNumber) throws NoSuchObjectException {
        Employee employee = employeeRepository.findEmployeeByPhoneNumber(phoneNumber);
        if (employee == null) {
            throw new NoSuchObjectException(Employee.class.toString());
        }
        return new EmployeeDto(employee);

    }

    List<EmployeeDto> findByTeamId(Long teamId) throws NoSuchObjectException {
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