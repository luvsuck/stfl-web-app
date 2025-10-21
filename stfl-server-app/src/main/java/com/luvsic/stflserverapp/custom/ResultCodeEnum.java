package com.luvsic.stflserverapp.custom;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(3200, "操作成功", ""),
    FAILED(3500, "访问失败", ""),
    VALIDATE_FAILED(3400, "参数校验失败", ""),
    METHOD_NOT_ALLOWED(3405, "方法不支持", ""),
    ACCESS_DENY(3403, "拒绝访问", ""),
    ERROR(3999, "接口错误", ""),
    USER_PRINCIPAL_NOT_FOUND(3000, "用户凭据不存在", ""),
    USER_NOT_EXISTED(3001, "用户不存在", ""),
    USERNAME_DUPLICATED(3002, "用户名冲突", ""),
    USER_DISABLED(3003, "用户被禁用", ""),

    PASSWORD_INCORRECT(3004, "密码不正确", ""),
    USER_NOT_LOGIN(3005, "用户未登录，请先登录", ""),
    USER_LOCKED(3006, "用户被锁定", ""),
    TOKEN_HAS_EXPIRED(3007, "token过期,请重新登录", ""),
    TOKEN_ILLEGAL(3008, "非法token", ""),
    TOKEN_WITH_ERROR(3009, "获取token出现异常", ""),
    CHANGE_PASSWORD_DENY(3010, "修改密码非法", ""),
    ACCOUNT_LOGIN_EMAIL_FORMAT_INCORRECT(3011, "账户邮箱格式错误", ""),
    ACCOUNT_PHONE_NUMBER_FORMAT_INCORRECT(3012, "账户手机号格式错误", ""),

    ;


    private final int code;
    private final String msg;
    private final String msgEn;

    ResultCodeEnum(int code, String msg, String msgEn) {
        this.code = code;
        this.msg = msg;
        this.msgEn = msgEn;
    }

    public static ResultCodeEnum getEnumByCode(int code) {
        for (ResultCodeEnum value : ResultCodeEnum.values()) {
            if (value.getCode() == code)
                return value;
        }
        return null;
    }
}

