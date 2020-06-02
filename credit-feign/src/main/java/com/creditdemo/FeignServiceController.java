package com.creditdemo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调用credit服务
 *
 */
@RestController
public class FeignServiceController {

    @Autowired
    private FeignServiceApi feignServiceApi;

    @GetMapping(value = "/userInfo/mapper")
    public Object getUserInfo(@RequestParam(value = "userNo", required = false) String userNo) {
        if (StringUtils.isBlank(userNo)) {
            return feignServiceApi.listAllUser();
        } else {
            return feignServiceApi.getBySubuserNo(userNo);
        }
    }

}
