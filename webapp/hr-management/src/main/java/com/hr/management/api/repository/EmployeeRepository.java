package com.hr.management.api.repository;

import com.hr.management.api.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(Long id);

    Employee findEmployeeByEmail(String email);

    Employee findEmployeeByPhoneNumber(String phoneNumber);

    List<Employee> findEmployeesByIdIn(List<Long> ids);
}
