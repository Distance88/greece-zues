package com.zhang.project.biz.manager;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.zhang.integration.user.client.UserClient;
import com.zhang.project.biz.util.OssUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Yaohang Zhang
 * @ClassName OssManager
 * @description TODO
 * @date 2021-10-15 10:19
 */
@Service
public class OssManager {

    @Resource
    private UserClient userClient;

    private final static String urlPrefix = "https://ngyst.oss-cn-hangzhou.aliyuncs.com/";

    public String uploadImage(MultipartFile multipartFile){

        OSS ossClient = OssUtils.getOssClient();
        Calendar calendar = Calendar.getInstance();
        String imagePath = calendar.get(Calendar.YEAR) +
                "/" + StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0')+
                "/" + StringUtils.leftPad(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0')+
                "/" + IdUtil.fastSimpleUUID() + "." +  multipartFile.getOriginalFilename().split("\\.")[1];

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest("ngyst",imagePath,new ByteArrayInputStream(multipartFile.getBytes()));
            ossClient.putObject(putObjectRequest);
            return StringUtils.join(urlPrefix,imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
        return null;
    }

    public void deleteImage(String fileName){
        OSS ossClient = OssUtils.getOssClient();
        ossClient.deleteObject("ngyst",fileName.substring(43));
        ossClient.shutdown();
    }

    @Transactional(rollbackFor = Exception.class)
    public String uploadAvatar(MultipartFile multipartFile,String userOpenId){
        String avatar = uploadImage(multipartFile);
        userClient.updateAvatarUserOpenId(userOpenId,avatar);
        return avatar;
    }
}
