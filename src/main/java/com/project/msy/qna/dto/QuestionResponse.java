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
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AnswerResponse answer;

    public QuestionResponse(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.content = q.getContent();
        this.userId = q.getUser().getId();
        this.createdAt = q.getCreatedAt();
        this.updatedAt = q.getUpdatedAt();
        if (q.getAnswer() != null) {
            this.answer = new AnswerResponse(q.getAnswer());
        }
    }
}
