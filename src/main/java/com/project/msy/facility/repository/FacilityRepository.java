package com.project.msy.facility.repository;

import com.project.msy.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    //    타입별로 조회
    List<Facility> findByType(String type);
}
