package com.nushad.quizapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nushad.quizapp.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{
    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM questions WHERE category = :category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> getRandomQuestionsByCategory(String category, Integer numQ);
}
