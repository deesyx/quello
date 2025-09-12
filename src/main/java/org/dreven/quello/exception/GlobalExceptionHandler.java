package org.dreven.quello.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreven.quello.controller.dto.base.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.dreven.quello.exception.GlobalErrorCodeConstants.*;

/**
 * 全局异常处理器，将 Exception 翻译成 CommonResult + 对应的异常编号
 *
 * @author pengchaoling
 */
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理 SpringMVC 请求参数缺失
     * <p>
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<CommonResult<?>> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        CommonResult<Object> error = CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<CommonResult<?>> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        CommonResult<Object> error = CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", constraintViolation.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     * <p>
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResult<?>> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        CommonResult<Object> error = CommonResult.error(METHOD_NOT_ALLOWED.getCode(), String.format("请求方法不正确:%s", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }


    /**
     * 处理业务异常 ServiceException
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<CommonResult<?>> serviceExceptionHandler(ServiceException ex) {
        log.info("[serviceExceptionHandler]", ex);
        CommonResult<Object> error = CommonResult.error(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = ServerException.class)
    public ResponseEntity<CommonResult<?>> serverExceptionHandler(ServerException ex) {
        log.info("[serverExceptionHandler]", ex);
        CommonResult<Object> error = CommonResult.error(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResult<?>> defaultExceptionHandler(Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        // 返回 ERROR CommonResult
        CommonResult<Object> error = CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMsg());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
