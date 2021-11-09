package com.zhang.project.biz.dto;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName UserInfoDTO
 * @description TODO
 * @date 2021-10-28 14:46
 */
@Data
public class UserInfoDTO {


    private UserItem userItem;

    private UserSkill userSkill;

    private UserHonor userHonor;

    @Data
    public class UserItem{


        private String itemName;

        private String itemRole;

        private String itemDept;

        private String itemBeginTime;

        private String itemEndTime;

        private String itemContent;
    }

    @Data
    public class UserSkill{

        private String content;
    }

    @Data
    public class UserHonor{

        private String content;
    }

}
