package org.dreven.quello.controller.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dreven.quello.common.enums.Period;

import java.time.LocalDate;

@Data
public class DashboardSearchReq {

    @Schema(description = "产品模块")
    private String productModule;

    @Schema(description = "开始日期")
    private LocalDate startDate;

    @Schema(description = "结束日期")
    private LocalDate endDate;

    @NotNull
    @Schema(description = "周期")
    private Period period;

    @JsonIgnore
    @Schema(description = "周期开始日期")
    private LocalDate periodStartDate;

    @JsonIgnore
    @Schema(description = "周期结束日期")
    private LocalDate periodEndDate;

    @JsonIgnore
    @Schema(description = "上一个周期开始日期")
    private LocalDate periodStartDatePre;

    @JsonIgnore
    @Schema(description = "上一个周期结束日期")
    private LocalDate periodEndDatePre;

}
