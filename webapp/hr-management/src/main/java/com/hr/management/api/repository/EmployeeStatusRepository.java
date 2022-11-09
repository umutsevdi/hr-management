package com.hr.management.api.repository;

import com.hr.management.api.repository.entity.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long> {

    EmployeeStatus findEmployeeStatusByEmployeeId(Long id);

    List<EmployeeStatus> findEmployeeStatusByTeamId(Long teamId);

    List<EmployeeStatus> findEmployeeStatusesByTitle(String title);

    List<EmployeeStatus> findEmployeeStatusesByTeamIdAndTitle(Long teamId, String title);

    List<EmployeeStatus> findEmployeeStatusesByEmployeeIdAndTeamId(Long id, Long teamId);

    List<EmployeeStatus> findEmployeeStatusesByEmployeeIdIn(List<Long> ids);

    List<EmployeeStatus> findEmployeeStatusesByTeamIdIn(List<Long> teamIds);
}
