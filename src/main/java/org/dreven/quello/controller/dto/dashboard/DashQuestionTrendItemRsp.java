package org.dreven.quello.controller.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DashQuestionTrendItemRsp {

    @Schema(description = "时间")
    private String time;

    @Schema(description = "数量")
    private Integer count;
}
