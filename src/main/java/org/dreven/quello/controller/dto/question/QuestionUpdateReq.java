package org.dreven.quello.controller.dto.question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dreven.quello.common.enums.QuestionPriority;
import org.dreven.quello.common.enums.QuestionSeverity;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.enums.QuestionType;

import java.time.LocalDate;

@Data
public class QuestionUpdateReq {

    @Schema(description = "问题ID")
    @NotNull
    private String questionId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "产品模块")
    private String productModule;

    @Schema(description = "问题类型")
    private QuestionType questionType;

    @Schema(description = "严重等级")
    private QuestionSeverity severity;

    @Schema(description = "当前状态")
    private QuestionStatus status;

    @Schema(description = "优先级")
    private QuestionPriority priority;

    @Schema(description = "计划解决时间")
    private LocalDate plannedResolutionDate;

    @Schema(description = "实际解决时间")
    private LocalDate actualResolutionDate;

    @Schema(description = "提出人")
    private String reportedBy;

    @Schema(description = "责任人")
    private String responsiblePerson;

    @Schema(description = "版本号")
    @NotNull
    private Long version;

}