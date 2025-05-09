package com.project.msy.qna.service;

import com.project.msy.qna.dto.AnswerResponse;
import com.project.msy.qna.dto.QuestionResponse;
import com.project.msy.qna.dto.QuestionRequest;
import java.util.List;

public interface QnaService {
    QuestionResponse createQuestion(Long userId, QuestionRequest dto);
    List<QuestionResponse> getUserQuestions(Long userId);
    QuestionResponse getQuestion(Long userId, Long questionId);
    QuestionResponse getQuestionById(Long questionId);
    List<QuestionResponse> getAllQuestions();
    QuestionResponse updateQuestion(Long userId, Long questionId, QuestionRequest dto);
    void deleteQuestion(Long userId, Long questionId);

    AnswerResponse createAnswer(Long questionId, String content);
    AnswerResponse getAnswer(Long questionId);
    AnswerResponse updateAnswer(Long questionId, String content);
    void deleteAnswer(Long questionId);

    void deleteQuestionByAdmin(Long id);
}
