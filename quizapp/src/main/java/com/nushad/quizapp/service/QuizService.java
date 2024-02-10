package com.nushad.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nushad.quizapp.dao.QuestionDao;
import com.nushad.quizapp.dao.QuizDao;
import com.nushad.quizapp.model.Question;
import com.nushad.quizapp.model.QuestionWrapper;
import com.nushad.quizapp.model.Quiz;
import com.nushad.quizapp.model.ResponseQuiz;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;
    
    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {

        List<Question> questions = questionDao.getRandomQuestionsByCategory(category, numQ);       
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz Created Successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        @SuppressWarnings("null")
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionForClient = new ArrayList<>();

        for(Question question: questionsFromDb){
            QuestionWrapper clientQ = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
            questionForClient.add(clientQ);
        }
        return new ResponseEntity<>(questionForClient, HttpStatus.OK);
        
    }

    public ResponseEntity<Integer> submitQuiz(Integer id, List<ResponseQuiz> answers) {
        @SuppressWarnings("null")
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questions = quiz.get().getQuestions();
        Integer right = 0;
        int i = 0;
        
        for (ResponseQuiz answer: answers){
            if (answer.getResponse().equals(questions.get(i).getRightAnswer())){
                right ++;
            }
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
    
}
