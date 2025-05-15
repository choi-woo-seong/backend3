package com.project.msy.qna.dto;

import com.project.msy.qna.entity.Question;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class QuestionResponse {
    private Long id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AnswerResponse answer;

    // ✅ 공통된 질문 대상
    private String targetType;    // "product", "facility", or "none"
    private Long targetId;
    private String targetName;

    public QuestionResponse(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.content = q.getContent();
        this.userId = q.getUser().getUserId();
        this.createdAt = q.getCreatedAt();
        this.updatedAt = q.getUpdatedAt();

        // ✅ 연관된 대상 분기 처리
        if (q.getProduct() != null) {
            this.targetType = "product";
            this.targetId = q.getProduct().getId();
            this.targetName = q.getProduct().getName();
        } else if (q.getFacility() != null) {
            this.targetType = "facility";
            this.targetId = q.getFacility().getId();
            this.targetName = q.getFacility().getName();
        } else {
            this.targetType = "none";
            this.targetId = null;
            this.targetName = null;
        }

        if (q.getAnswer() != null) {
            this.answer = new AnswerResponse(q.getAnswer());
        }
    }
}
