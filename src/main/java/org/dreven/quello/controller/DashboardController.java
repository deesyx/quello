package org.dreven.quello.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dreven.quello.controller.dto.base.CommonResult;
import org.dreven.quello.controller.dto.dashboard.DashboardSearchReq;
import org.dreven.quello.controller.dto.question.QuestionDTO;
import org.dreven.quello.service.DashboardService;
import org.dreven.quello.service.QuestionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "看板")
@Validated
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/overview")
    @Operation(summary = "整体情况")
    public CommonResult<QuestionDTO> getOverview(@RequestBody DashboardSearchReq req) {
        return CommonResult.success(dashboardService.getOverview(req));
    }
}
