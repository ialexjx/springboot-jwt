package com.jwt_auth.controllers;

import com.jwt_auth.models.responses.ApiResponse;
import com.jwt_auth.models.tables.QuestionsAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crud")
public class QuesAnsController {
    @Autowired
    private QuestionAnswerService questionAnswerService;

    //http://localhost:8080/crud/hello
    @GetMapping("/hello")
    public ResponseEntity<?> printHello() {
        System.out.println("inside the print hello method in the crud controller class");
        return new ResponseEntity<>("Hello World!, This is a testing method", HttpStatusCode.valueOf(200));
    }

    @GetMapping("get-ques/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable int id) {
        System.out.println("inside the getQuestionById method");
        Optional<QuestionsAnswers> optionalQuestionsAnswers = questionAnswerService.findById(id);
        if (optionalQuestionsAnswers.isEmpty()) {
            ApiResponse<QuestionsAnswers> response = new ApiResponse<>(400, null, "No Data Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            ApiResponse<QuestionsAnswers> response = new ApiResponse<>(200, optionalQuestionsAnswers.get(), "Operation Successful");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("getAllQuesAns")
    public ResponseEntity<?> getAllQuesAns() {
        System.out.println("inside the get all method");
        List<QuestionsAnswers> questionsAnswersList = questionAnswerService.getAllQuesAns();
        ApiResponse<List<QuestionsAnswers>> response = new ApiResponse<>(200, questionsAnswersList, "Data is here");
        return ResponseEntity.ok(response);
    }
}
