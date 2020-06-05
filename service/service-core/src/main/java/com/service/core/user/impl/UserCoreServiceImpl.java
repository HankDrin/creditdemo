package com.service.core.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.service.core.user.IUserCoreService;
import com.service.dal.dao.user.UserBaseInfoMapper;
import com.service.dal.model.user.UserBaseInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户核心服务
 *
 */
@Component
public class UserCoreServiceImpl implements IUserCoreService {

    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;

    //    @Override
//    @PrintLog
//    public boolean realNameRegister(UserBaseInfoDO userBaseInfo) {
//        return userBaseInfo.insert();
//    }

    @Override
    public UserBaseInfoDO getByIdNo(Integer idType, String idNo) {
        LambdaQueryWrapper<UserBaseInfoDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(UserBaseInfoDO::getIdType, idType)
                          .eq(UserBaseInfoDO::getIdNo, idNo);
        return userBaseInfoMapper.selectOne(lambdaQueryWrapper);
    }

}
