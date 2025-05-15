package com.project.msy.qna.dto;

import lombok.Getter;
import com.project.msy.qna.entity.Question;
import java.time.LocalDateTime;

/**
 * 질문 응답 DTO
 */
@Getter
public class QuestionResponse {
    private Long id;
    private String title;
    private String content;
    private String userId;      // ✅ 사용자의 계정 ID (문자열)
    private Long userDbId;      // ✅ 사용자 PK (숫자 ID) - 프론트에서 비교용
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AnswerResponse answer;
    private Long productId;
    private Long facilityId;

    public QuestionResponse(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.content = q.getContent();
        this.userId = q.getUser().getUserId();       // ex: "goqus903"
        this.userDbId = q.getUser().getId();         // ex: 12
        this.createdAt = q.getCreatedAt();
        this.updatedAt = q.getUpdatedAt();
        this.productId = q.getProduct() != null ? q.getProduct().getId() : null;
        this.facilityId = q.getFacilityId();
        if (q.getAnswer() != null) {
            this.answer = new AnswerResponse(q.getAnswer());
        }
    }
}
