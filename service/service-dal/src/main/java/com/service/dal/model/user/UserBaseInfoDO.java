/*
* create by mybatis-plus-generator
*/
package com.service.dal.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.service.dal.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户基本信息表
 * </p>
 *
 * @author xxx
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_base_info")
public class UserBaseInfoDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 会员编号
     */
    @TableField("subuser_no")
    private String subuserNo;

    /**
     * 会员名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 证件类型 1 身份证，2 军官证，3 护照，4 户口簿，5 士兵证，6 港澳来往内地通行证，7 台湾同胞来往内地通行证，8 临时身份证，9 外国人居留证，10 警官证
     */
    @TableField("id_type")
    private Integer idType;

    /**
     * 证件号码
     */
    @TableField("id_no")
    private String idNo;

    /**
     * 手机号码
     */
    @TableField("mobile_no")
    private String mobileNo;

    /**
     * 用户登陆密码
     */
    @TableField("password")
    private String password;


}
