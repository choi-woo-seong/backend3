package com.project.msy.review.service.impl;

import com.project.msy.facility.entity.Facility;
import com.project.msy.facility.repository.FacilityRepository;
import com.project.msy.review.dto.FacilityReviewDto;
import com.project.msy.review.entity.FacilityReview;
import com.project.msy.review.repository.FacilityReviewRepository;
import com.project.msy.review.service.FacilityReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityReviewServiceImpl implements FacilityReviewService {

    private final FacilityReviewRepository reviewRepository;
    private final FacilityRepository facilityRepository;

    @Override
    public List<FacilityReviewDto> getReviewsByFacilityId(Long facilityId) {
        return reviewRepository.findByFacilityId(facilityId).stream()
                .map(review -> FacilityReviewDto.builder()
                        .id(review.getId())
                        .facilityId(review.getFacility().getId())
                        .userName(review.getUserName())
                        .rating(review.getRating())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FacilityReviewDto createReview(FacilityReviewDto dto) {
        Facility facility = facilityRepository.findById(dto.getFacilityId())
                .orElseThrow(() -> new IllegalArgumentException("시설이 존재하지 않습니다."));

        FacilityReview review = FacilityReview.builder()
                .facility(facility)
                .userName(dto.getUserName())
                .rating(dto.getRating())
                .content(dto.getContent())
                .build();

        FacilityReview saved = reviewRepository.save(review);

        return FacilityReviewDto.builder()
                .id(saved.getId())
                .facilityId(facility.getId())
                .userName(saved.getUserName())
                .rating(saved.getRating())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
