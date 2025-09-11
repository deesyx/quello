package org.dreven.quello.controller;

import lombok.RequiredArgsConstructor;
import org.dreven.quello.controller.dto.QuestionDTO;
import org.dreven.quello.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    
    private final QuestionService questionService;
    
    @GetMapping("/detail/{questionId}")
    public QuestionDTO getDetail(@PathVariable String questionId) {
        return questionService.getDetail(questionId);
    }
}