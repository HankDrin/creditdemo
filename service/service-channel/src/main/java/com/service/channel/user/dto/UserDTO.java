package com.service.channel.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @NotBlank(message = "用户姓名不能为空")
    private String userName;

    @NotBlank(message = "用户手机号不能为空")
    private String mobileNo;

    @NotNull(message = "用户证件类型不能为空")
    private Integer idType;

    @NotBlank(message = "用户证件号不能为空")
    private String idNo;

}
