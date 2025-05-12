package com.project.msy.facility.controller;

import com.project.msy.facility.dto.FacilityCreateRequestDTO;
import com.project.msy.facility.dto.FacilityResponseDTO;
import com.project.msy.facility.entity.Facility;
import com.project.msy.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FacilityController {

    private final FacilityService facilityService;

    //    admin create
    @PostMapping(value = "/facility/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Facility> createFacility(
            @RequestPart("dto") FacilityCreateRequestDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        Facility created = facilityService.createFacility(dto, images);
        return ResponseEntity.ok(created);
    }

    //    admin read
    @GetMapping("/facility")
    public ResponseEntity<List<FacilityResponseDTO>> getFacilities(@RequestParam(required = false) String type) {
        if (type != null) {
            return ResponseEntity.ok(facilityService.getFacilitiesByType(type));
        } else {
            return ResponseEntity.ok(facilityService.getAllFacilities());
        }
    }

    //    admin facility 한개만 read
    @GetMapping("/facility/{id}")
    public ResponseEntity<FacilityResponseDTO> getFacility(@PathVariable Long id) {
        FacilityResponseDTO dto = facilityService.getFacilityById(id);
        return ResponseEntity.ok(dto);
    }

    //    태그별 list조회
    @GetMapping("/facility/search")
    public ResponseEntity<List<FacilityResponseDTO>> searchFacilities(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String facilitySize,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String region // 🔥 추가
    ) {
        List<FacilityResponseDTO> result = facilityService.searchFacilities(type, grade, facilitySize, sort, region);
        return ResponseEntity.ok(result);
    }



    // UPDATE
    @PutMapping(value = "/facility/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FacilityResponseDTO> updateFacility(
            @PathVariable Long id,
            @RequestPart("dto") FacilityCreateRequestDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        FacilityResponseDTO updated = facilityService.updateFacility(id, dto, images);
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/facility/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.noContent().build();
    }


}