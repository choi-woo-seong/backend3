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
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AnswerResponse answer;
    private Long productId;


    public QuestionResponse(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.content = q.getContent();
        this.userId = q.getUser().getUserId();
        this.createdAt = q.getCreatedAt();
        this.updatedAt = q.getUpdatedAt();
        this.productId = q.getProduct().getId(); // ✅ 이 줄 추가!
        if (q.getAnswer() != null) {
            this.answer = new AnswerResponse(q.getAnswer());
        }
    }
}
