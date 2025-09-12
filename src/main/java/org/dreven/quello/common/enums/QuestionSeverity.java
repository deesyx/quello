package org.dreven.quello.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionSeverity {

    SERIOUS("严重"),
    HIGH("高"),
    MEDIUM("中"),
    LOW("低");

    private final String value;
}
