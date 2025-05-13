package com.project.msy.facility.repository;

import com.project.msy.admin.dashboard.dto.FacilityTypeDto;
import com.project.msy.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    //    타입별로 조회
    List<Facility> findByType(String type);

    @Query("SELECT f FROM Facility f " +
            "WHERE (:type IS NULL OR f.type = :type) " +
            "AND (:facilitySize IS NULL OR f.facilitySize = :facilitySize) " +
            "AND (:grade IS NULL OR f.grade = :grade)")
    List<Facility> findByConditions(@Param("type") String type,
                                    @Param("facilitySize") String facilitySize,
                                    @Param("grade") String grade);
//    지역 세부 설정을 하기위한  jpa
public List<Facility> findByAddressContainingIgnoreCase(String region);

    @Query("SELECT new com.project.msy.admin.dashboard.dto.FacilityTypeDto(f.type, COUNT(f)) " +
            "FROM Facility f GROUP BY f.type")
    List<FacilityTypeDto> countByType();

}
