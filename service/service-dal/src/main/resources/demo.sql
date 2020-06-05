-- 用户基本信息表
DROP TABLE IF EXISTS user_base_info;
/*==============================================================*/
/* Table: user_base_info                                        */
/*==============================================================*/
CREATE TABLE user_base_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    subuser_no VARCHAR ( 64 ) NOT NULL COMMENT '会员编号',
    user_name VARCHAR ( 32 ) NOT NULL COMMENT '会员名称',
    id_type TINYINT NOT NULL COMMENT '证件类型 1 身份证，2 军官证，3 护照，4 户口簿，5 士兵证，6 港澳来往内地通行证，7 台湾同胞来往内地通行证，8 临时身份证，9 外国人居留证，10 警官证' ,
    id_no VARCHAR ( 32 ) NOT NULL COMMENT '证件号码',
    mobile_no VARCHAR ( 32 ) NOT NULL COMMENT '手机号码',
    password VARCHAR (128) NOT NULL COMMENT '用户登陆密码',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0未删除,1删除，默认0',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    PRIMARY KEY ( id )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT '用户基本信息表';