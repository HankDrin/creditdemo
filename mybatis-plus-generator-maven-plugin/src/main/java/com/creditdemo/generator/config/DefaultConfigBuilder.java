package com.creditdemo.generator.config;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;

/**
 * TODO
 *
 * @author chenhongding
 * @since 2020-06-01.
 */
public class DefaultConfigBuilder extends ConfigBuilder {
    /**
     * <p>
     * 在构造器中处理配置
     * </p>
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param template         模板配置
     * @param globalConfig     全局配置
     */
    public DefaultConfigBuilder(DefaultPackageConfig packageConfig, DataSourceConfig dataSourceConfig,
            StrategyConfig strategyConfig, TemplateConfig template, GlobalConfig globalConfig) {
        super(packageConfig, dataSourceConfig, strategyConfig, template, globalConfig);
        getPackageInfo().put("controllerModule", packageConfig.getControllerModule());
    }
}
