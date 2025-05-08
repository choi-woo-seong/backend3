package com.project.msy.facility.dto;

import com.project.msy.facility.entity.FacilitySize;
import com.project.msy.facility.entity.FacilityType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FacilitySearchRequestDTO {
    private FacilityType type;
    private String region;
    private String keyword;
    private String sort;
    private FacilitySize facilitySize;
    private String evaluationGrade;
    private String specialization;
}
