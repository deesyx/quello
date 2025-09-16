package org.dreven.quello.controller.dto.question;

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

    /**
     * 标题
     */
    @NotEmpty
    private String title;

    /**
     * 内容
     */
    @NotEmpty
    private String content;

    /**
     * 产品模块
     */
    @NotNull
    private String productModule;

    /**
     * 问题类型
     */
    @NotNull
    private QuestionType questionType;

    /**
     * 严重等级
     */
    @NotNull
    private QuestionSeverity severity;

    /**
     * 优先级
     */
    @NotNull
    private QuestionPriority priority;

    /**
     * 计划解决时间
     */
    @NotNull
    private LocalDate plannedResolutionDate;

    /**
     * 提出人
     */
    @NotNull
    private String reportedBy;

    /**
     * 责任人
     */
    @NotNull
    private String responsiblePerson;

}