package com.zhang.integration.user.fallback;

import com.zhang.integration.user.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Yaohang Zhang
 * @ClassName UserClientFallBack
 * @description TODO
 * @date 2021-10-10 23:05
 */
@Component
@Slf4j
public class UserClientFallBack implements UserClient {
    @Override
    public String findNameByUserOpenId(String openId) {
        log.error("根据用户openId获取姓名进入了熔断器");
        return "张三";
    }
}
