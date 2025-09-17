package org.dreven.quello.controller.dto.question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dreven.quello.common.enums.QuestionPriority;
import org.dreven.quello.common.enums.QuestionSeverity;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.enums.QuestionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class QuestionCreateReq {

    @Schema(description = "标题")
    @NotEmpty
    private String title;

    @Schema(description = "内容")
    @NotEmpty
    private String content;

    @Schema(description = "产品模块")
    @NotNull
    private String productModule;

    @Schema(description = "问题类型")
    @NotNull
    private QuestionType questionType;

    @Schema(description = "严重等级")
    @NotNull
    private QuestionSeverity severity;

    @Schema(description = "优先级")
    @NotNull
    private QuestionPriority priority;

    @Schema(description = "计划解决时间")
    @NotNull
    private LocalDate plannedResolutionDate;

    @Schema(description = "提出人")
    @NotNull
    private String reportedBy;

    @Schema(description = "责任人")
    @NotNull
    private String responsiblePerson;

}