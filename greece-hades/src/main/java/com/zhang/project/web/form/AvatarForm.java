package com.zhang.project.web.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Yaohang Zhang
 * @ClassName AvatarForm
 * @description TODO
 * @date 2021-10-19 14:20
 */
@Data
public class AvatarForm {

    private MultipartFile multipartFile;

    private String userOpenId;
}
