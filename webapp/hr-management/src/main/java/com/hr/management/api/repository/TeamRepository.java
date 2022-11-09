package com.hr.management.api.repository;

import com.hr.management.api.repository.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findTeamByName(String name);

    Team findTeamByBossId(Long id);

}
