package org.dreven.quello.controller.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class QuestionDTO {
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
    private String questionType;

    /**
     * 严重等级
     */
    private String severity;

    /**
     * 当前状态
     */
    private String status;

    /**
     * 优先级
     */
    private String priority;

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