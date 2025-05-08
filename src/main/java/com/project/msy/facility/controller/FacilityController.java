package com.project.msy.facility.controller;

import com.project.msy.facility.dto.FacilitySearchRequestDTO;
import com.project.msy.facility.dto.FacilitySearchResponseDTO;
import com.project.msy.facility.entity.FacilitySize;
import com.project.msy.facility.entity.FacilityType;
import com.project.msy.facility.service.FacilitySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilitySearchService facilitySearchService;

    @GetMapping("/search")
    public ResponseEntity<List<FacilitySearchResponseDTO>> searchFacilities(
            @RequestParam FacilityType type,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) FacilitySize facilitySize,
            @RequestParam(required = false) String evaluationGrade,
            @RequestParam(required = false) String specialization
    ) {
        FacilitySearchRequestDTO request = FacilitySearchRequestDTO.builder()
                .type(type)
                .region(region)
                .keyword(keyword)
                .sort(sort)
                .facilitySize(facilitySize)
                .evaluationGrade(evaluationGrade)
                .specialization(specialization)
                .build();

        return ResponseEntity.ok(facilitySearchService.searchFacilities(request));
    }
}
