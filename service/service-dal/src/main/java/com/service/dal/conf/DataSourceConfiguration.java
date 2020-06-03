package com.service.dal.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * TODO
 *
 * @author chenhongding
 * @since 2020-05-31.
 */
@Configuration
@PropertySource(value = "classpath:application-dal.yml", encoding = "utf-8", factory = YamlPropertyResourceFactory.class)
@Slf4j
public class DataSourceConfiguration {

//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource generateDataSource() throws Exception {
//        log.info("Druid数据源配置");
//        return new DruidDataSource();
//    }
}
