package com.project.msy.qna.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 질문 등록/수정 요청 DTO
 */
@Getter
@Setter
public class QuestionRequest {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    // 상품 문의용
    private Long productId;

    // 시설 문의용
    private Long facilityId;
}
