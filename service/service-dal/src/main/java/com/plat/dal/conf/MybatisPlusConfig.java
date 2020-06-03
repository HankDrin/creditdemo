/*------------------------------------------------------------------------------
 * COPYRIGHT Transfar Pay 2018
 *
 * The copyright to the computer program(s) herein is the property of
 * Transfar Pay Inc. The programs may be used and/or copied only with written
 * permission from Transfar Pay Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.plat.dal.conf;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置类
 * MybatisPlusConfig
 */
@Configuration
@MapperScan("com.creditdemo.dal.dao.*")
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     * 
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
