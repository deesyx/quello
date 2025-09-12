package org.dreven.quello.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionPriority {
    P0("P0"),
    P1("P1"),
    P2("P2"),
    P3("P3");

    private final String value;
}
