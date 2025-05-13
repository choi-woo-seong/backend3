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

    @NotNull(message = "상품 ID는 필수입니다") // ✅ productId는 반드시 있어야 함
    private Long productId;
}
