package com.tff.creditpay.common.enums.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统编码枚举类
 *
 * @auther 20314@etransfar.com(zhangbin)
 * @CreateDate 2018年9月3日 下午5:35:40
 */
@Getter
@AllArgsConstructor
public enum SysCodeMsgEnum {
    /**
     * 成功
     */
    SUCCESS("GP_00", "成功"),
    /**
     * 系统正忙
     */
    SYS_BUSY("GP_050999", "系统正忙"),
    /**
     * 系统异常
     */
    SYS_ERROR("GP_050998", "系统异常");

    /**
     * 编码CODE
     */
    private String code;
    /**
     * 编码MSG
     */
    private String msg;
}
