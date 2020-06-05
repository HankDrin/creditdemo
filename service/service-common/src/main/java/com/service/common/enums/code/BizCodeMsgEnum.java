package com.service.common.enums.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务异常编码枚举类
 *
 */
@Getter
@AllArgsConstructor
public enum BizCodeMsgEnum {

    SUCCESS("GPBIZ_00", "成功"),
    FAILURE("GPBIZ_050000", "失败"),

    PARAMETER_ERROR("GPBIZ_050301", "请求参数有误"),

    /**********USER**********/
    USER_REGISTERED("GPBIZ_000001", "用户已注册，请直接登陆！"),
    USER_NOT_LOGIN("GPBIZ_000002", "用户未登陆，请先登陆！"),;

    /**
     * 编码CODE
     */
    private String code;
    /**
     * 编码MSG
     */
    private String msg;

}
