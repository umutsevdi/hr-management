package com.hr.management.api.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/*
 * The team that users work in
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table
@Entity(name = "team")
public class Team extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "boss_id",nullable = false)
    private Long bossId;
    @Column
    private String profile;
    @Column(name = "is_remote")
    private boolean isRemote;
    @Column(name = "ip_address")
    private String ipAddress;
}
