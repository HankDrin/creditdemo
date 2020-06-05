package com.service.core.user;

import com.service.dal.model.user.UserBaseInfoDO;

/**
 * 用户核心服务
 *
 */
public interface IUserCoreService {

//    boolean realNameRegister(UserBaseInfoDO userBaseInfo);

    /**
     * 根据证件信息查询用户信息
     *
     * @param idType 证件类型
     * @param idNo   身份证
     * @return 用户信息
     */
    UserBaseInfoDO getByIdNo(Integer idType, String idNo);

}
