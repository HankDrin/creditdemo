package com.service.biz.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.service.biz.user.IUserBizService;
import com.service.common.annotation.PrintLog;
import com.service.core.user.IUserCoreService;
import com.service.dal.dao.user.UserBaseInfoMapper;
import com.service.dal.model.user.UserBaseInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;

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
    @PrintLog
    public boolean realNameRegister(String userName, String mobileNo, Integer idType, String idNo) {
        log.info("开始用户实名登记: {}", userName);
        return userCoreService.realNameRegister(userName, mobileNo, idType, idNo);
    }

    /**
     * 根据会员号取用户信息
     *
     * @param subuserNo
     * @return
     */
    @Override
    @PrintLog
    public UserBaseInfoDO getBySubuserNo(String subuserNo) {
        UserBaseInfoDO condition = new UserBaseInfoDO().setSubuserNo(subuserNo);
        QueryWrapper<UserBaseInfoDO> wrapper = new QueryWrapper<>(condition);
        List<UserBaseInfoDO> result = userBaseInfoMapper.selectList(wrapper);
        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    /**
     * 根据会员号删除用户信息
     *
     * @param subuserNo
     * @return
     */
    @Override
    @PrintLog
    public boolean delBySubuserNo(String subuserNo) {
        QueryWrapper<UserBaseInfoDO> wrapper = new QueryWrapper<>();
        wrapper.eq("subuser_no", subuserNo);

        // 逻辑删
        return userBaseInfoMapper.delete(wrapper) > 0;
    }
}
