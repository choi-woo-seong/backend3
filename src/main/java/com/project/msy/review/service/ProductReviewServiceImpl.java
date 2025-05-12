package com.project.msy.review.service;

import com.project.msy.product.entity.Product;
import com.project.msy.product.repository.ProductRepository;
import com.project.msy.review.dto.ProductReviewDto;
import com.project.msy.review.entity.ProductReview;
import com.project.msy.review.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductReviewDto createReview(ProductReviewDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        ProductReview review = ProductReview.builder()
                .product(product)
                .userName(dto.getUserName())
                .rating(dto.getRating())
                .content(dto.getContent())
                .build();

        return toDto(reviewRepository.save(review));
    }

    @Override
    public List<ProductReviewDto> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private ProductReviewDto toDto(ProductReview entity) {
        return ProductReviewDto.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .userName(entity.getUserName())
                .rating(entity.getRating())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
