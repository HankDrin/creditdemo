package com.service.biz.user;

import com.service.biz.user.dto.UserDTO;
import com.service.common.security.AuthObject;
import com.service.dal.model.user.UserBaseInfoDO;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户模块业务服务
 *
 * @author chenhongding
 * @since 2020-05-31.
 */
public interface IUserBizService extends UserDetailsService {

    /**
     * 用户实名注册
     *
     * @param user 用户注册信息
     * @return 登记结果
     */
    UserBaseInfoDO register(UserDTO user);

    /**
     * 用户登陆
     *
     * @param user 用户登陆信息
     * @return 登陆认证对象
     */
    AuthObject login(UserDTO user);

    /**
     * 根据会员号取用户信息
     *
     * @param subuserNo
     * @return
     */
    UserBaseInfoDO getBySubuserNo(String subuserNo);

    /**
     * 根据会员号删除用户信息
     *
     * @param subuserNo
     * @return
     */
    boolean delBySubuserNo(String subuserNo);

}
