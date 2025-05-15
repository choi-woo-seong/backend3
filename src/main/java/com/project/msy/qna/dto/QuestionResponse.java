package com.project.msy.qna.dto;

import com.project.msy.qna.entity.Question;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 질문 응답 DTO
 */
@Getter
public class QuestionResponse {
    private Long id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AnswerResponse answer;
    private Long productId;    // 상품 ID
    private Long facilityId;   // 시설 ID

    public QuestionResponse(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.content = q.getContent();
        this.userId = q.getUser().getUserId();
        this.createdAt = q.getCreatedAt();
        this.updatedAt = q.getUpdatedAt();

        // ✅ 상품 ID 또는 시설 ID 세팅
        if (q.getProduct() != null) {
            this.productId = q.getProduct().getId();
        }
        if (q.getFacility() != null) {
            this.facilityId = q.getFacility().getId();
        }

        if (q.getAnswer() != null) {
            this.answer = new AnswerResponse(q.getAnswer());
        }
    }
}
