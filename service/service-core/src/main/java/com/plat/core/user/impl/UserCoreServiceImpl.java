package com.plat.core.user.impl;

import com.plat.core.user.IUserCoreService;
import com.plat.dal.model.user.UserBaseInfoDO;
import org.springframework.stereotype.Component;

/**
 * 用户核心服务
 *
 * @author chenhongding
 * @since 2020-05-31.
 */
@Component
public class UserCoreServiceImpl implements IUserCoreService {

    /**
     * 用户实名登记
     *
     * @param userName 用户名
     * @param mobileNo 手机号
     * @param idType   证件类型
     * @param idNo     证件号
     * @return 登记结果
     */
    @Override
    public boolean realNameRegister(String userName, String mobileNo, Integer idType, String idNo) {
        UserBaseInfoDO userBaseInfo = new UserBaseInfoDO();
        userBaseInfo.setSubuserNo("123")
                    .setUserName(userName)
                    .setMobileNo(mobileNo)
                    .setIdType(idType)
                    .setIdNo(idNo);
        return userBaseInfo.insert();
    }
}
