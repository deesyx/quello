package org.dreven.quello.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionStatus {
    PENDING("待处理"),
    PROCESSING("处理中"),
    REVIEWING("待评审"),
    RESOLVED("已解决"),
    IN_INSPECTION("验收中"),
    CLOSED("已关闭"),
    COMPLETED("已完成");

    private final String value;
}
