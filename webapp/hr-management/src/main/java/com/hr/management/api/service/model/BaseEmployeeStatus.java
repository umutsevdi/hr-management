package com.hr.management.api.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEmployeeStatus {
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


    public BaseEmployeeStatus(Long employeeId, Long teamId, Long workingHour, Integer completedSprints, Integer awaitingTasks, Integer completedTasks, Integer delayedTasks, Integer unfinishedTasks, Float averageTeamScore, Double monthlySalary) {
        this.employeeId = employeeId;
        this.teamId = teamId;
        this.workingHour = workingHour;
        this.completedSprints = completedSprints;
        this.awaitingTasks = awaitingTasks;
        this.completedTasks = completedTasks;
        this.delayedTasks = delayedTasks;
        this.unfinishedTasks = unfinishedTasks;
        this.averageTeamScore = averageTeamScore;
        this.monthlySalary = monthlySalary;
    }
}
