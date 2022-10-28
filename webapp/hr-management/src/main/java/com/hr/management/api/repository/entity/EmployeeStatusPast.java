package com.hr.management.api.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table
@Entity(name = "e_status_past")
public class EmployeeStatusPast extends BaseEntity {
    @Column(name = "e_id", nullable = false, unique = true)
    private Long employeeId;
    @Column(name = "t_id", nullable = false)
    private Long teamId;
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
    @Column
    private Integer year;
    @Column(name = "work_days_late")
    private Integer workDaysLate;
    @Column(name = "work_days_overtime")
    private Integer workDaysOvertime;

}
