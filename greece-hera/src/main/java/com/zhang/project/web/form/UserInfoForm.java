package com.zhang.project.web.form;

import lombok.Data;

/**
 * @author Yaohang Zhang
 * @ClassName UserInfoForm
 * @description TODO
 * @date 2021-10-28 17:32
 */
@Data
public class UserInfoForm {

    private String userOpenId;

    private UserItem userItem;

    private UserSkill userSkill;

    private UserHonor userHonor;

    @Data
    public  class UserItem{


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
