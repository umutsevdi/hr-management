package com.hr.management.api.repository.entity;

import com.hr.management.api.service.model.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * The team that users work in
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table
@Entity(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    // Generic information
    @Column(name = "CREATED")
    private LocalDateTime createdDate;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "boss_id", nullable = false)
    private Long bossId;
    @Column
    private String profile;
    @Column(name = "is_remote")
    private boolean isRemote;
    @Column(name = "ip_address")
    private String ipAddress;

    public Team(TeamDto team) {
        this(
                team.getId(),
                team.getCreatedDate(),
                team.getName(),
                team.getBossId(),
                team.getProfile(),
                team.isRemote(),
                team.getIpAddress()
        );
    }
}
