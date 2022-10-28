package com.hr.management.api.service.model;

import com.hr.management.api.repository.entity.EmployeeStatusPast;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hr.management.api.repository.entity.EmployeeStatusPast} entity
 */
@Getter
@Setter
public class EmployeeStatusPastDto extends EmployeeStatusDto implements Serializable {
    private Integer year;
    private Integer workDaysLate;
    private Integer workDaysOvertime;

    public EmployeeStatusPastDto(EmployeeStatusPast employeeStatus) {
        super(
                employeeStatus.getEmployeeId(),
                employeeStatus.getTeamId(),
                employeeStatus.getWorkingHour(),
                employeeStatus.getCompletedSprints(),
                employeeStatus.getAwaitingTasks(),
                employeeStatus.getCompletedTasks(),
                employeeStatus.getDelayedTasks(),
                employeeStatus.getUnfinishedTasks(),
                employeeStatus.getAverageTeamScore(),
                employeeStatus.getMonthlySalary()
        );
        this.year = employeeStatus.getYear();
        this.workDaysLate = employeeStatus.getWorkDaysLate();
        this.workDaysOvertime = employeeStatus.getWorkDaysOvertime();
    }
}