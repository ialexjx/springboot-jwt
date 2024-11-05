package com.jwt_auth.repositories;

import com.jwt_auth.models.tables.QuestionsAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionsAnswers, Long> {

}
