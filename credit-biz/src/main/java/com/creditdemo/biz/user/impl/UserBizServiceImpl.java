package com.creditdemo.biz.user.impl;

import com.creditdemo.biz.user.IUserBizService;
import com.creditdemo.core.user.IUserCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 * @author chenhongding
 * @since 2020-05-31.
 */
@Service
@Slf4j
public class UserBizServiceImpl implements IUserBizService {

    @Autowired
    private IUserCoreService userCoreService;
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
        log.info("开始用户实名登记: {}", userName);
        return userCoreService.realNameRegister(userName, mobileNo, idType, idNo);
    }
}
