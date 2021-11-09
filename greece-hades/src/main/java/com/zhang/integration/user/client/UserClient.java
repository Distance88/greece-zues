package com.zhang.integration.user.client;

import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.integration.user.fallback.UserClientFallBack;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    String findNameByUserOpenId(@RequestParam("openId") String openId);

    @GetMapping("hera/user/findAvatarUserOpenId")
    String findAvatarUserOpenId(@RequestParam("openId") String openId);

    @PostMapping("hera/user/updateAvatarUserOpenId")
    void updateAvatarUserOpenId(@RequestParam("openId")String openId,@RequestParam("avatar")String avatar);

    @GetMapping("hera/user/userInfo/list")
    CommonRestResult getUserInfoList();
}
