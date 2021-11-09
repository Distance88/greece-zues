package com.zhang.integration.user.client;

import com.zhang.integration.user.fallback.UserClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Yaohang Zhang
 * @ClassName UserClient
 * @description TODO
 * @date 2021-10-10 23:04
 */
@Component
@FeignClient(value = "greece-hera",fallback = UserClientFallBack.class)
public interface UserClient {

    @GetMapping("hera/user/findNameByOpenId")
    String findNameByUserOpenId(@RequestParam("openId")String openId);
}
