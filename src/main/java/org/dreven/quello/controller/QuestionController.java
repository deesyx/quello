package org.dreven.quello.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dreven.quello.controller.dto.question.QuestionCreateReq;
import org.dreven.quello.controller.dto.question.QuestionDTO;
import org.dreven.quello.controller.dto.base.CommonResult;
import org.dreven.quello.controller.dto.base.PageResult;
import org.dreven.quello.controller.dto.question.QuestionSearchReq;
import org.dreven.quello.controller.dto.question.QuestionUpdateReq;
import org.dreven.quello.service.QuestionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "问题")
@Validated
@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{questionId}/detail")
    @Operation(summary = "获取问题详情")
    public CommonResult<QuestionDTO> getDetail(@PathVariable String questionId) {
        return CommonResult.success(questionService.getDetail(questionId));
    }

    @PostMapping("/search")
    @Operation(summary = "搜索问题")
    public CommonResult<PageResult<QuestionDTO>> search(@RequestBody QuestionSearchReq req) {
        return CommonResult.success(questionService.search(req));
    }

    @PostMapping("/creation")
    @Operation(summary = "创建问题")
    public CommonResult<Boolean> createQuestion(@Valid @RequestBody QuestionCreateReq req) {
        return CommonResult.success(questionService.createQuestion(req));
    }

    @PostMapping("/update")
    @Operation(summary = "更新问题")
    public CommonResult<Boolean> updateQuestion(@Valid @RequestBody QuestionUpdateReq req) {
        return CommonResult.success(questionService.updateQuestion(req));
    }
}