package com.zhang.integration.user.fallback;

import com.zhang.common.core.restful.CommonRestResult;
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

    @Override
    public String findAvatarUserOpenId(String openId) {
        log.error("根据用户openId获取头像链接进入了熔断器");
        return "https://ngyst.oss-cn-hangzhou.aliyuncs.com/2021/05/22/5213c128-de47-4339-8b82-05acabcf0774.jpg";
    }

    @Override
    public void updateAvatarUserOpenId(String openId, String avatar) {
        log.error("根据用户openId修改头像链接进入了熔断器");
    }

    @Override
    public CommonRestResult getUserInfoList() {
        log.error("获取用户信息列表进入了熔断器");
        return null;
    }
}
