package com.service.biz.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.service.biz.user.IUserBizService;
import com.service.biz.user.dto.UserDTO;
import com.service.common.annotation.PrintLog;
import com.service.common.enums.code.BizCodeMsgEnum;
import com.service.common.exception.ApplicationException;
import com.service.common.security.AuthObject;
import com.service.common.security.PasswordAuthToken;
import com.service.core.user.IUserCoreService;
import com.service.dal.dao.user.UserBaseInfoMapper;
import com.service.dal.model.user.UserBaseInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private TokenService tokenService;

    /**
     * 用户实名登记
     *
     * @param user 用户注册信息
     * @return 登记结果
     */
    @Override
    @PrintLog
    public UserBaseInfoDO register(UserDTO user) {
        UserBaseInfoDO userBaseInfo = userCoreService.getByIdNo(user.getIdType(), user.getIdNo());
        if (Objects.nonNull(userBaseInfo)) {
            log.warn("用户已注册，请直接登陆。");
            throw new ApplicationException(BizCodeMsgEnum.USER_REGISTERED);
        }

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        userBaseInfo = new UserBaseInfoDO();
        userBaseInfo.setSubuserNo(UUID.randomUUID().toString())
                    .setMobileNo(user.getMobileNo())
                    .setIdType(user.getIdType())
                    .setIdNo(user.getIdNo())
                    .setUserName(user.getUserName())
                    .setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        userBaseInfo.insert();
        return userBaseInfo;
    }

    /**
     * 用户登陆
     *
     * @param user 用户登陆信息
     * @return 登陆认证对象
     */
    @Override
    @PrintLog
    public AuthObject login(UserDTO user) {
        PasswordAuthToken passwordAuthToken = new PasswordAuthToken(user.getSubUserNo(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(passwordAuthToken);
        // 认证放入线程上下文
        AuthObject authUser = (AuthObject) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 认证放入缓存
        String key = authUser.getUserId() + authUser.getIdNo();
        Token token = tokenService.allocateToken(key);
        authUser.setToken(token.getKey());
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("user", key, authUser);
        return authUser;
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
//        LambdaQueryChainWrapper<UserBaseInfoDO> queryChainWrapper = new LambdaQueryChainWrapper<>(userBaseInfoMapper);
//        List<UserBaseInfoDO> result = queryChainWrapper.eq(UserBaseInfoDO::getSubuserNo, subuserNo).list();
        LambdaQueryWrapper<UserBaseInfoDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserBaseInfoDO::getSubuserNo, subuserNo);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBaseInfoDO userBaseInfoDO = getBySubuserNo(username);
        if (Objects.isNull(userBaseInfoDO)) {
            throw new UsernameNotFoundException("用户不存在，请先注册！");
        }
        AuthObject authObject = new AuthObject();
        authObject.setUserId(userBaseInfoDO.getSubuserNo());
        authObject.setUserName(userBaseInfoDO.getUserName());
        authObject.setIdNo(userBaseInfoDO.getIdNo());
        authObject.setPassword(userBaseInfoDO.getPassword());
        return authObject;
    }
}
