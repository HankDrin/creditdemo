package com.service.common.aspect.log;

import com.service.common.enums.log.LogLevelEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统自动打印方法日志注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreditAutoLog {

    /**
     * 日志级别， 默认INFO
     *
     */
    LogLevelEnum level() default LogLevelEnum.INFO;

    /**
     * 是否打印方法返回结果
     * 默认为打印
     *
     */
    boolean isPrintReturnResult() default true;

}
