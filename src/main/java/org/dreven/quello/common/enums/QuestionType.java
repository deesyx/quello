package org.dreven.quello.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionType {
    FUNCTION("功能"),
    INTERACTION("交互"),
    PERFORMANCE("性能"),
    VISUAL("视觉"),
    DATA("数据"),
    LOGIC("逻辑"),
    SAFE("安全"),
    OTHER("其他");

    private final String value;
}
