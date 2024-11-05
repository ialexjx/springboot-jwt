package com.jwt_auth.services;

import com.jwt_auth.models.tables.QuestionsAnswers;
import com.jwt_auth.repositories.QuestionAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionAnswerService {

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    public Optional<QuestionsAnswers> findById(int id) {
        return questionAnswerRepository.findById((long) id);
    }

    public List<QuestionsAnswers> getAllQuesAns() {
        return questionAnswerRepository.findAll();
    }
}
