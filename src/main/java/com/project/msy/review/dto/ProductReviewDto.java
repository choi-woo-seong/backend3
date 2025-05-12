package com.project.msy.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewDto {
    private Long id;
    private Long productId;

    private String userName;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
}
