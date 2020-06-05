package com.service.channel.user;

import com.service.biz.user.IUserBizService;
import com.service.biz.user.dto.UserDTO;
import com.service.biz.user.dto.validated.LoginValidGroup;
import com.service.biz.user.dto.validated.RegisterValidGroup;
import com.service.common.annotation.PrintLog;
import com.service.common.constant.SysConstants;
import com.service.common.security.AuthObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户认证服务
 *
 * @author xxx
 * @since 2020-06-04.
 */
@RestController
@RequestMapping("/user/auth")
@Slf4j
public class UserAuthController {

    @Autowired
    private IUserBizService userBizService;

    /**
     * 用户信息注册
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    @PrintLog
    public Object register(@RequestBody @Validated(RegisterValidGroup.class) UserDTO userDTO) {
        return userBizService.register(userDTO);
    }

    @PostMapping("/login")
    @PrintLog
    public Object login(@RequestBody @Validated(LoginValidGroup.class) UserDTO userDTO, HttpServletResponse response) {
        AuthObject authObject = userBizService.login(userDTO);
        response.addCookie(makeCookie(SysConstants.TOKEN_NAME, authObject.getToken()));
        return authObject;
    }

    private Cookie makeCookie(String name, String token) {
        Cookie cookie = new Cookie(name, token);
        cookie.setPath("/");
        return cookie;
    }

}
