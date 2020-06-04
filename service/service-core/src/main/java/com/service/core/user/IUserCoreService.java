package com.service.core.user;

/**
 * 用户核心服务
 *
 */
public interface IUserCoreService {

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

}
