package com.project.msy.facility.service;

import com.project.msy.facility.dto.FacilitySearchRequestDTO;
import com.project.msy.facility.dto.FacilitySearchResponseDTO;
import com.project.msy.facility.entity.Facility;
import com.project.msy.facility.entity.FacilityDetail;
import com.project.msy.facility.entity.FacilityImage;
import com.project.msy.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilitySearchService {
    private final FacilityRepository facilityRepository;

    @Transactional(readOnly = true)
    public List<FacilitySearchResponseDTO> searchFacilities(FacilitySearchRequestDTO request) {

        List<Facility> facilities = facilityRepository.findByType(request.getType());

        return facilities.stream()

                // 지역 필터
                .filter(f -> request.getRegion() == null || f.getAddress().contains(request.getRegion()))

                // 키워드 필터
                .filter(f -> request.getKeyword() == null ||
                        f.getName().contains(request.getKeyword()) || f.getAddress().contains(request.getKeyword()))

                // 시설규모 필터 (이제 Enum 비교로 간단함)
                .filter(f -> request.getFacilitySize() == null || f.getFacilitySize() == request.getFacilitySize())

                // 평가등급 필터
                .filter(f -> {
                    if (request.getEvaluationGrade() == null) return true;
                    if (f.getFacilityDetails().isEmpty()) return request.getEvaluationGrade().equals("등급제외");
                    String grade = f.getFacilityDetails().get(0).getRating() != null ?
                            f.getFacilityDetails().get(0).getRating().name() + "등급" : "등급제외";
                    return grade.equals(request.getEvaluationGrade());
                })

                // 특화영역 필터
                .filter(f -> {
                    if (request.getSpecialization() == null) return true;
                    return f.getSpecializations().stream()
                            .anyMatch(s -> s.getName().equals(request.getSpecialization()));
                })

                // DTO 변환
                .map(f -> {

                    String mainImageUrl = f.getFacilityImages().stream()
                            .filter(FacilityImage::getIsMain)
                            .findFirst()
                            .map(FacilityImage::getImageUrl)
                            .orElse("");

                    List<String> tags = new ArrayList<>();

                    if (!f.getFacilityDetails().isEmpty()) {
                        FacilityDetail detail = f.getFacilityDetails().get(0);
                        tags.add(detail.getRating() != null ? detail.getRating().name() + "등급" : "등급제외");
                    }

                    tags.add(f.getFacilitySize().name()); // 시설규모 추가

                    int years = LocalDateTime.now().getYear() - f.getEstablishedYear();
                    tags.add("설립 " + years + "년");

                    f.getSpecializations().forEach(s -> tags.add(s.getName()));

                    return FacilitySearchResponseDTO.fromEntity(f, mainImageUrl, tags);
                })

                // 정렬
                .sorted(getComparator(request.getSort()))
                .collect(Collectors.toList());
    }

    private Comparator<FacilitySearchResponseDTO> getComparator(String sort) {

        if (sort == null || sort.equals("추천순")) {
            return Comparator.comparing(FacilitySearchResponseDTO::getId).reversed();
        } else if (sort.equals("조회순")) {
            return Comparator.comparing(FacilitySearchResponseDTO::getId).reversed(); // viewCount 기준으로 수정 가능
        } else if (sort.equals("상담많은순")) {
            return Comparator.comparing(FacilitySearchResponseDTO::getId).reversed(); // 추후 상담수 추가시 수정 가능
        } else if (sort.equals("후기많은순")) {
            return Comparator.comparing(FacilitySearchResponseDTO::getReviewCount).reversed();
        } else if (sort.equals("찜많은순")) {
            return Comparator.comparing(FacilitySearchResponseDTO::getId).reversed(); // likeCount 기준으로 수정 가능
        }

        return Comparator.comparing(FacilitySearchResponseDTO::getId).reversed();
    }
}
