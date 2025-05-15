package com.project.msy.review.service;

import com.project.msy.review.dto.FacilityReviewDto;

import java.util.List;

public interface FacilityReviewService {
    List<FacilityReviewDto> getReviewsByFacilityId(Long facilityId);
    FacilityReviewDto createReview(FacilityReviewDto dto);
}
