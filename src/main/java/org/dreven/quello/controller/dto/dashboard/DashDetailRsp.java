package org.dreven.quello.controller.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DashDetailRsp {

    @Schema(description = "整体情况")
    private DashboardOverviewRsp overview;

    @Schema(description = "状态分布")
    private List<DashDistributionItemRsp> statusDistributions;

    @Schema(description = "严重等级分布")
    private List<DashDistributionItemRsp> severityDistributions;

    @Schema(description = "问题类型分布")
    private List<DashDistributionItemRsp> questionTypeDistributions;

    @Schema(description = "产品模块分布")
    private List<DashDistributionItemRsp> productModuleDistributions;

    @Schema(description = "高频参考方案")
    private List<DashDistributionItemRsp> questionFrequencyDistributions;
}
