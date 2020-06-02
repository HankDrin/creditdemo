/*
* create by mybatis-plus-generator
*/
package com.creditdemo.channel.user;

import com.creditdemo.biz.user.IUserBaseInfoService;
import com.creditdemo.biz.user.IUserBizService;
import com.creditdemo.channel.user.dto.UserDTO;
import com.creditdemo.dal.model.user.UserBaseInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private IUserBaseInfoService userBaseInfoService;

    @PostMapping("/register/realName")
    public Object registerUserRealName(@RequestBody UserDTO userDTO) {
        log.info("接入用户实名请求: {}", userDTO);
        return userBizService
                .realNameRegister(userDTO.getUserName(), userDTO.getMobileNo(), userDTO.getIdType(), userDTO.getIdNo());
    }

    @GetMapping("/listAll")
    public Object listAllUser() {
        return new UserBaseInfoDO().selectAll();
    }

    @GetMapping("/get/{subuserNo}")
    public Object getBySubuserNo(@PathVariable String subuserNo) {
        return userBizService.getBySubuserNo(subuserNo);
    }

    @PostMapping("del")
    public Object deleteUser(@RequestParam("subuser_no") String subuserNo) {
        return userBizService.delBySubuserNo(subuserNo);
    }
}
