package org.dreven.quello.controller.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DashDistributionItemRsp {

    @Schema(description = "标签")
    private String key;

    @Schema(description = "值")
    private Integer value;
}
