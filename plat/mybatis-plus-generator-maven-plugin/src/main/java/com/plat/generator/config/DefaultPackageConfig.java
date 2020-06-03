package com.plat.generator.config;

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
     * Controller模块名
     */
    private String controllerModule = "";

}
