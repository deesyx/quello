package org.dreven.quello.controller.dto.question;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dreven.quello.common.enums.QuestionPriority;
import org.dreven.quello.common.enums.QuestionSeverity;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.enums.QuestionType;

import java.time.LocalDate;

@Data
public class QuestionUpdateReq {

    /**
     * 问题ID
     */
    @NotNull
    private String questionId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 产品模块
     */
    private String productModule;

    /**
     * 问题类型
     */
    private QuestionType questionType;

    /**
     * 严重等级
     */
    private QuestionSeverity severity;

    /**
     * 当前状态
     */
    private QuestionStatus status;

    /**
     * 优先级
     */
    private QuestionPriority priority;

    /**
     * 计划解决时间
     */
    private LocalDate plannedResolutionDate;

    /**
     * 实际解决时间
     */
    private LocalDate actualResolutionDate;

    /**
     * 提出人
     */
    private String reportedBy;

    /**
     * 责任人
     */
    private String responsiblePerson;

    /**
     * 版本号
     */
    @NotNull
    private Long version;

}