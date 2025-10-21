package com.luvsic.stflserverapp.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 16:49
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = -5920830964620773030L;
    private int code;
    private String msg;
    private T data;

    public R(T data) {
        this(ResultCodeEnum.SUCCESS, data);
    }

    public R() {
        this(ResultCodeEnum.SUCCESS);
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
    }

    public R(ResultCodeEnum resultCodeEnum, T data) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        this.data = data;
        // System.out.println(data.toString());
    }

    public R(ResultCodeEnum resultCodeEnum, String additionalInfo, T data) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg() + " , " + additionalInfo;
        this.data = data;
    }

    public static <T> R<T> success(T t) {
        return new R<>(t);
    }

    public static R<ResultCodeEnum> success() {
        return new R<>();
    }

    public static R<?> fail(Integer code, String msg) {
        return new R<>(code, msg);
    }

    public static <T> R<T> fail(T t) {
        return new R<>(ResultCodeEnum.FAILED, t);
    }

    public static <T> R<T> error(T t) {
        return new R<>(ResultCodeEnum.ERROR, t);
    }

    public static <T> R<T> fail(int code, String msg, T t) {
        return new R<>(code, msg, t);
    }

    public static <T> R<T> missParam(T t, String msg) {
        return new R<>(ResultCodeEnum.VALIDATE_FAILED, ResultCodeEnum.VALIDATE_FAILED.getMsg(), t);
    }
}
