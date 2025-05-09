package com.project.msy.facility.service;

import com.project.msy.S3.S3Uploader;
import com.project.msy.facility.dto.FacilityCreateRequestDTO;
import com.project.msy.facility.dto.FacilityResponseDTO;
import com.project.msy.facility.entity.Facility;
import com.project.msy.facility.entity.FacilityImage;
import com.project.msy.facility.repository.FacilityImageRepository;
import com.project.msy.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final FacilityImageRepository facilityImageRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Facility createFacility(FacilityCreateRequestDTO dto, List<MultipartFile> imageFiles) throws IOException {
        Facility facility = Facility.builder()
                .type(dto.getType())
                .name(dto.getName())
                .establishedYear(dto.getEstablishedYear())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .homepage(dto.getHomepage())
                .grade(dto.getGrade())
                .description(dto.getDescription())
                .weekdayHours(dto.getWeekdayHours())
                .weekendHours(dto.getWeekendHours())
                .holidayHours(dto.getHolidayHours())
                .visitingHours(dto.getVisitingHours())
                .facilitySize(dto.getFacilitySize())
                .build();

        Facility savedFacility = facilityRepository.save(facility);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<FacilityImage> images = imageFiles.stream()
                    .map(file -> {
                        try {
                            String url = s3Uploader.upload(file, "facilities");
                            return FacilityImage.builder()
                                    .facility(savedFacility)
                                    .imageUrl(url)
                                    .build();
                        } catch (IOException e) {
                            throw new RuntimeException("S3 업로드 실패", e);
                        }
                    })
                    .collect(Collectors.toList());
            facilityImageRepository.saveAll(images);
        }

        return savedFacility;
    }

    public List<FacilityResponseDTO> getAllFacilities() {
        return facilityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<FacilityResponseDTO> getFacilitiesByType(String type) {
        return facilityRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FacilityResponseDTO convertToDTO(Facility facility) {
        return FacilityResponseDTO.builder()
                .name(facility.getName())
                .type(facility.getType())
                .address(facility.getAddress())
                .createdAt(facility.getCreatedAt())
                .build();
    }

}
