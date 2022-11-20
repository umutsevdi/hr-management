package com.hr.management.api.repository;

import com.hr.management.api.repository.entity.EmployeeStatusPast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeStatusPastRepository extends JpaRepository<EmployeeStatusPast, Long> {
    List<EmployeeStatusPast> findEmployeeStatusPastsByEmployeeId(Long employeeId);

    List<EmployeeStatusPast> findEmployeeStatusPastByTeamId(Long teamId);

    List<EmployeeStatusPast> findEmployeeStatusPastsByEmployeeIdAndTeamId(Long id, Long teamId);

    List<EmployeeStatusPast> findEmployeeStatusPastByEmployeeIdAndYear(Long id, int year);

    List<EmployeeStatusPast> findEmployeeStatusPastByTeamIdAndYear(Long teamId, int year);

    List<EmployeeStatusPast> findEmployeeStatusPastsByTeamIdIn(List<Long> teamIds);
}
