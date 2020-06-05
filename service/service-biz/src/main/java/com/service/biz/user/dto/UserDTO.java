package com.service.biz.user.dto;

import com.service.biz.user.dto.validated.LoginValidGroup;
import com.service.biz.user.dto.validated.RegisterValidGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @NotBlank(message = "用户姓名不能为空", groups = { RegisterValidGroup.class})
    private String userName;

    @NotBlank(message = "用户手机号不能为空", groups = { RegisterValidGroup.class})
    private String mobileNo;

    @NotNull(message = "用户证件类型不能为空", groups = { RegisterValidGroup.class})
    private Integer idType;

    @NotBlank(message = "用户证件号不能为空", groups = { RegisterValidGroup.class})
    private String idNo;

    @NotBlank(message = "用户编号不能为空", groups = { LoginValidGroup.class })
    private String subUserNo;

    @NotBlank(message = "用户密码不能为空", groups = { RegisterValidGroup.class, LoginValidGroup.class })
    private String password;

}
