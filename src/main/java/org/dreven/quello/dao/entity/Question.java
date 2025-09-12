package org.dreven.quello.dao.entity;

import lombok.Data;
import org.dreven.quello.common.enums.QuestionPriority;
import org.dreven.quello.common.enums.QuestionSeverity;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.enums.QuestionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Question {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 问题ID
     */
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
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
