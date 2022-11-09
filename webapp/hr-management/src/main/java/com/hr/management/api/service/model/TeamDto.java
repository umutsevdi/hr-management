package com.hr.management.api.service.model;

import com.hr.management.api.repository.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.hr.management.api.repository.entity.Team} entity
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class TeamDto implements Serializable {
    private Long id;
    private LocalDateTime createdDate;
    private String name;
    private Long bossId;
    private String profile;
    private boolean isRemote;
    private String ipAddress;

    public TeamDto(Team team) {
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