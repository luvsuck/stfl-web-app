package com.luvsic.stflserverapp.custom.exception;

import com.luvsic.stflserverapp.custom.R;
import com.luvsic.stflserverapp.custom.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 18:42
 * @description
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public R<?> authenticationException(AuthenticationException e) {
//        log.error("认证授权失败: {}", e.getMessage());
//        return new R<>(ResultCodeEnum.USER_NOT_LOGIN, "认证授权失败");
//    }
//
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public R<?> jwtExceptionHandler(ExpiredJwtException e) {
//        log.error("token过期: {}", e.getMessage());
//        return new R<>(ResultCodeEnum.TOKEN_HAS_EXPIRED, "token过期");
//    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> customExceptionHandler(BusinessException e) {
//        log.error("自定义异常:{}", e.getMessage());
        if (Objects.equals(e.getCode(), 0)) {
            e.setCode(ResultCodeEnum.ERROR.getCode());
            e.setMessage(e.getMessage());
        }

        return R.fail(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> methodArgNotValidExceptionHandler(MethodArgumentNotValidException e) {
//        log.error("方法参数校验不通关");
        List<String> list = new ArrayList<>();
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                list.add(error.getDefaultMessage());
            }
        }
        return new R<>(ResultCodeEnum.VALIDATE_FAILED, list);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> bindExceptionHandler(BindException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new R<>(ResultCodeEnum.VALIDATE_FAILED, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("请求参数缺失: {}", e.getMessage());
        return new R<>(ResultCodeEnum.VALIDATE_FAILED, "参数缺失: " + e.getParameterName());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R<Object> methodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("方法不支持: {}", e.getMessage());
        return new R<>(ResultCodeEnum.METHOD_NOT_ALLOWED, "方法不支持: " + e.getMethod());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public R<Object> accessDenyExceptionHandler(AccessDeniedException e) {
        log.error("访问被拒");
        return new R<>(ResultCodeEnum.ACCESS_DENY, e.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public R<Object> runtimeExceptionHandler(RuntimeException e) {
        log.error("运行错误,cause:{},msg:{}", e.getCause(), e.getMessage());
        e.printStackTrace();
        return new R<>(ResultCodeEnum.ERROR);
    }


}