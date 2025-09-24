package org.dreven.quello.controller.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DashDetailRsp {

    @Schema(description = "整体情况")
    private DashboardOverviewRsp overview;

}
