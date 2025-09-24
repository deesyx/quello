package org.dreven.quello.controller.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "整体情况")
public class DashboardOverviewRsp {

    @Schema(description = "问题总数")
    private Integer totalQuestions;

    @Schema(description = "问题总数-增长率")
    private BigDecimal totalQuestionsGr;

    @Schema(description = "新增问题")
    private Integer newQuestions;

    @Schema(description = "新增问题-增长率")
    private BigDecimal newQuestionsGr;

    @Schema(description = "采纳率")
    private BigDecimal adoptionRate;

    @Schema(description = "采纳率-增长率")
    private BigDecimal adoptionRateGr;

    @Schema(description = "已解决问题")
    private Integer resolvedQuestions;

    @Schema(description = "已解决问题-增长率")
    private BigDecimal resolvedQuestionsGr;

    @Schema(description = "超时率")
    private BigDecimal overtimeRate;

    @Schema(description = "超时率-增长率")
    private BigDecimal overtimeRateGr;

    @Schema(description = "按期解决率")
    private BigDecimal onScheduleResolutionRate;

    @Schema(description = "按期解决率-增长率")
    private BigDecimal onScheduleResolutionRateGr;
}
