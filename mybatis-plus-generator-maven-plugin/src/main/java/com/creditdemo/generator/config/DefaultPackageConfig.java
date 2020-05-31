package com.creditdemo.generator.config;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TODO
 *
 * @author chenhongding
 * @since 2020-05-31.
 */
@Data
@Accessors(chain = true)
public class DefaultPackageConfig extends PackageConfig {
    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String controllerModule = "";

}
