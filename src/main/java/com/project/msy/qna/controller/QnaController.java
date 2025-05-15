package com.project.msy.qna.controller;

import com.project.msy.qna.dto.QuestionRequest;
import com.project.msy.qna.dto.QuestionResponse;
import com.project.msy.qna.dto.AnswerRequest;
import com.project.msy.qna.dto.AnswerResponse;
import com.project.msy.qna.service.QnaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService service;

    // ----- User endpoints -----

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @Valid @RequestBody QuestionRequest dto
    ) {
        QuestionResponse created = service.createQuestion(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/my")
    public ResponseEntity<List<QuestionResponse>> getMyQuestions(
            @AuthenticationPrincipal(expression = "id") Long userId
    ) {
        List<QuestionResponse> list = service.getUserQuestions(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/my/{id}")
    public ResponseEntity<QuestionResponse> getMyQuestionById(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable Long id
    ) {
        QuestionResponse resp = service.getQuestion(userId, id);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/my/{id}")
    public ResponseEntity<QuestionResponse> updateMyQuestion(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequest dto
    ) {
        QuestionResponse updated = service.updateQuestion(userId, id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/my/{id}")
    public ResponseEntity<Void> deleteMyQuestion(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable Long id
    ) {
        service.deleteQuestion(userId, id);
        return ResponseEntity.noContent().build();
    }

    // ----- Admin endpoints -----

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getQuestionsByTarget(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long facilityId
    ) {
        if (productId != null) {
            return ResponseEntity.ok(service.getQuestionsByProductId(productId));
        } else if (facilityId != null) {
            return ResponseEntity.ok(service.getQuestionsByFacilityId(facilityId));
        } else {
            return ResponseEntity.ok(service.getAllQuestions());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        QuestionResponse resp = service.getQuestionById(id);
        return ResponseEntity.ok(resp);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{questionId}/answer")
    public ResponseEntity<AnswerResponse> createAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerRequest dto
    ) {
        AnswerResponse created = service.createAnswer(questionId, dto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{questionId}/answer")
    public ResponseEntity<AnswerResponse> getAnswer(@PathVariable Long questionId) {
        AnswerResponse resp = service.getAnswer(questionId);
        return ResponseEntity.ok(resp);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{questionId}/answer")
    public ResponseEntity<AnswerResponse> updateAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerRequest dto
    ) {
        AnswerResponse updated = service.updateAnswer(questionId, dto.getContent());
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{questionId}/answer")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long questionId) {
        service.deleteAnswer(questionId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionByAdmin(@PathVariable Long id) {
        service.deleteQuestionByAdmin(id); // 별도 메서드 또는 같은 deleteQuestion 재사용
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/facility/{id}")
    public ResponseEntity<List<QuestionResponse>> getFacilityQuestions(@PathVariable Long id) {
        return ResponseEntity.ok(service.getQuestionsByFacilityId(id));
    }
}