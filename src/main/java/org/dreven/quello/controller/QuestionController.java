package org.dreven.quello.controller;

import lombok.RequiredArgsConstructor;
import org.dreven.quello.controller.dto.question.QuestionDTO;
import org.dreven.quello.controller.dto.base.CommonResult;
import org.dreven.quello.controller.dto.base.PageResult;
import org.dreven.quello.controller.dto.question.QuestionSearchReq;
import org.dreven.quello.service.QuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{questionId}/detail")
    public CommonResult<QuestionDTO> getDetail(@PathVariable String questionId) {
        return CommonResult.success(questionService.getDetail(questionId));
    }

    @PostMapping("/search")
    public CommonResult<PageResult<QuestionDTO>> search(@RequestBody QuestionSearchReq req) {
        return CommonResult.success(questionService.search(req));
    }
}