/*
* create by mybatis-plus-generator
*/
package com.creditdemo.channel.user;

import com.creditdemo.biz.user.IUserBizService;
import com.creditdemo.channel.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户基本信息表 前端控制器
 * </p>
 *
 * @author xxx
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private IUserBizService userBizService;

    @PostMapping("/register/realName")
    public Object registerUserRealName(@RequestBody UserDTO userDTO) {
        log.info("接入用户实名请求: {}", userDTO);
        userBizService.realNameRegister(userDTO.getUserName(), userDTO.getMobileNo(), userDTO.getIdType(), userDTO.getIdNo());
        return Boolean.TRUE;
    }
}
