package com.project.msy.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FacilityResponseDTO {
    private String name;
    private String type;
    private String address;
    private LocalDateTime createdAt;
}