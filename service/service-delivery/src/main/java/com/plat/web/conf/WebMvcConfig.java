package com.plat.web.conf;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.plat.common.tool.jackson.SnakeCaseDeserializer;
import com.plat.common.tool.jackson.SnakeCaseKeySerializer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class WebMvcConfig {

    @Bean
    public ResultModelWrapperConverter getResultModelWrapperConverter() {
        return new ResultModelWrapperConverter();
    }

    /**
     * 自定义jackson反序列化策略(Map类型驼峰Key转下划线)
     * 该bean在容器中由spring-boot自动识别配置,
     * 详见{@link JacksonAutoConfiguration}
     *
     * @return
     */
    @Bean
    public SimpleModule getSimpleModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeySerializer(String.class, new SnakeCaseKeySerializer());
        simpleModule.addDeserializer(Map.class, new SnakeCaseDeserializer());
        return simpleModule;
    }
}
