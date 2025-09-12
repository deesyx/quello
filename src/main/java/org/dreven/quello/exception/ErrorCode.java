package org.dreven.quello.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 错误码对象
 * <p>
 * TODO 错误码设计成对象的原因，为未来的 i18 国际化做准备
 */
@Data
@AllArgsConstructor
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;
}
