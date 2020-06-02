package com.creditdemo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 调用credit服务
 */
@FeignClient(value = "credit")
public interface FeignServiceApi {

    @RequestMapping(value = "/credit/user/get/{subuserNo}", method = RequestMethod.GET)
    Object getBySubuserNo(@PathVariable String subuserNo);

    @RequestMapping(value = "/credit/user/listAll", method = RequestMethod.GET)
    Object listAllUser();

}
