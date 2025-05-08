package com.project.msy.facility.dto;

import com.project.msy.facility.entity.Facility;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilitySearchResponseDTO {
    private Long id;
    private String category;
    private String name;
    private String address;
    private String imgSrc;
    private List<String> tags;
    private Double rating;
    private Integer reviewCount;

    public static FacilitySearchResponseDTO fromEntity(Facility facility, String imgSrc, List<String> tags) {

        return FacilitySearchResponseDTO.builder()
                .id(facility.getId())
                .category(facility.getType().toString())
                .name(facility.getName())
                .address(facility.getAddress())
                .imgSrc(imgSrc)
                .tags(tags)
                .rating(0.0)
                .reviewCount(0)
                .build();
    }
}
