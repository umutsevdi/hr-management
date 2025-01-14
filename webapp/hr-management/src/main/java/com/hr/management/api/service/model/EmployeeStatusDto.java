package com.hr.management.api.service.model;

import com.hr.management.api.repository.entity.EmployeeStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hr.management.api.repository.entity.EmployeeStatus} entity
 */
@Getter
@Setter
@Accessors(chain = true)
public class EmployeeStatusDto extends BaseEmployeeStatus implements Serializable {
    protected String title;

    public EmployeeStatusDto(EmployeeStatus employeeStatus) {
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
        this.title = employeeStatus.getTitle();
    }

    public EmployeeStatusDto(
            Long employeeId, Long teamId, Long workingHour,
            Integer completedSprints, Integer awaitingTasks,
            Integer completedTasks, Integer delayedTasks,
            Integer unfinishedTasks, Float averageTeamScore,
            Double monthlySalary, String title) {
        super(
                employeeId,
                teamId,
                workingHour,
                completedSprints,
                awaitingTasks,
                completedTasks,
                delayedTasks,
                unfinishedTasks,
                averageTeamScore,
                monthlySalary
        );
        this.title = title;
    }
}