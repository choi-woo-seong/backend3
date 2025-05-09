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
}