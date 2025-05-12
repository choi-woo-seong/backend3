package com.project.msy.stats.repository;

import com.project.msy.stats.entity.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {
    Optional<Stats> findByTypeAndTargetTypeAndTargetIdAndUnitAndStatDate(
            Stats.StatsType type,
            Stats.TargetType targetType,
            Long targetId,
            Stats.TimeUnit unit,
            LocalDate statDate
    );
}