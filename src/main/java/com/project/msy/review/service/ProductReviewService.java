package com.project.msy.review.service;

import com.project.msy.review.dto.ProductReviewDto;

import java.util.List;

public interface ProductReviewService {
    ProductReviewDto createReview(ProductReviewDto dto);
    List<ProductReviewDto> getReviewsByProductId(Long productId);
    void deleteReview(Long reviewId);
}
