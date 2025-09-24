package org.dreven.quello.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dreven.quello.controller.dto.base.CommonResult;
import org.dreven.quello.controller.dto.dashboard.DashDetailRsp;
import org.dreven.quello.controller.dto.dashboard.DashQuestionTrendItemRsp;
import org.dreven.quello.controller.dto.dashboard.DashboardSearchReq;
import org.dreven.quello.service.DashboardService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "看板")
@Validated
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PostMapping("/detail")
    @Operation(summary = "看板详情")
    public CommonResult<DashDetailRsp> getDetail(@Valid @RequestBody DashboardSearchReq req) {
        return CommonResult.success(dashboardService.getDetail(req));
    }

    @PostMapping("/question-trends")
    @Operation(summary = "新增问题趋势")
    public CommonResult<List<DashQuestionTrendItemRsp>> getQuestionTrends(@Valid @RequestBody DashboardSearchReq req) {
        return CommonResult.success(dashboardService.getQuestionTrends(req));
    }
}
