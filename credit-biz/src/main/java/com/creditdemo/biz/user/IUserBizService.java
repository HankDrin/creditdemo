package com.creditdemo.biz.user;

import com.creditdemo.dal.model.user.UserBaseInfoDO;

/**
 * 用户模块业务服务
 *
 * @author chenhongding
 * @since 2020-05-31.
 */
public interface IUserBizService {

    /**
     * 用户实名登记
     *
     * @param userName 用户名
     * @param mobileNo 手机号
     * @param idType   证件类型
     * @param idNo     证件号
     * @return 登记结果
     */
    boolean realNameRegister(String userName, String mobileNo, Integer idType, String idNo);

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
