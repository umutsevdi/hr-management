package com.hr.management.api.service.model;

import com.hr.management.api.repository.entity.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hr.management.api.repository.entity.EmployeeStatus} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmployeeStatusDto implements Serializable {
    protected Long employeeId;
    protected Long teamId;
    protected Long workingHour;
    protected Integer completedSprints;
    protected Integer awaitingTasks;
    protected Integer completedTasks;
    protected Integer delayedTasks;
    protected Integer unfinishedTasks;
    protected Float averageTeamScore;
    protected Double monthlySalary;

    public EmployeeStatusDto(EmployeeStatus employeeStatus) {
        this(
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
    }
}