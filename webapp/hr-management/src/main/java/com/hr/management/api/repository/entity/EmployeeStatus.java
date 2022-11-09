package com.hr.management.api.repository.entity;


import com.hr.management.api.service.model.EmployeeStatusDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table
@Entity(name = "e_status")
public class EmployeeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "e_id", nullable = false, unique = true)
    private Long employeeId;
    @Column(name = "t_id", nullable = false)
    private Long teamId;
    @Column
    private String title;
    @Column(name = "working_hour")
    private Long workingHour;
    @Column(name = "sprint")
    private Integer completedSprints;
    @Column(name = "sprint_tasks_awaiting")
    private Integer awaitingTasks;
    @Column(name = "tasks_completed")
    private Integer completedTasks;
    @Column(name = "tasks_delayed")
    private Integer delayedTasks;
    @Column(name = "tasks_incomplete")
    private Integer unfinishedTasks;
    @Column(name = "team_score_avg")
    private Float averageTeamScore;
    @Column(name = "monthly_salary")
    private Double monthlySalary;

    public EmployeeStatus(EmployeeStatusDto employeeStatus) {
        this(
                null,
                employeeStatus.getEmployeeId(),
                employeeStatus.getTeamId(),
                employeeStatus.getTitle(),
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
