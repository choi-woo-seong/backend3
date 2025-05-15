package com.project.msy.qna.repository;

import com.project.msy.qna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByUserId(Long userId);
}