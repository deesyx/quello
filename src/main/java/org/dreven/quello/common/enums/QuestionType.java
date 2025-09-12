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
    OTHER("其他");

    private final String value;
}
