/*
* create by mybatis-plus-generator
*/
package com.service.channel.user;

import com.service.biz.user.IUserBaseInfoService;
import com.service.biz.user.IUserBizService;
import com.service.channel.base.ResultModel;
import com.service.channel.user.dto.UserDTO;
import com.service.common.annotation.PrintLog;
import com.service.dal.model.user.UserBaseInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    @PrintLog
    public Object registerUserRealName(@RequestBody @Validated UserDTO userDTO) {
        return userBizService
                .realNameRegister(userDTO.getUserName(), userDTO.getMobileNo(), userDTO.getIdType(), userDTO.getIdNo());
    }

    @GetMapping("/listAll")
    @PrintLog
    public Object listAllUser() {
        return new UserBaseInfoDO().selectAll();
    }

    @GetMapping("/get/{subuserNo}")
    @PrintLog
    public Object getBySubuserNo(@PathVariable String subuserNo) {
        return ResultModel.buildSuccessResult(userBizService.getBySubuserNo(subuserNo));
    }

    @PostMapping("del")
    @PrintLog
    public Object deleteUser(@RequestParam("subuser_no") String subuserNo) {
        return userBizService.delBySubuserNo(subuserNo);
    }
}
