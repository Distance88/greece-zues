package com.zhang.project.biz.manager;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.core.exception.BizCoreException;
import com.zhang.project.biz.dto.UserInfoDTO;
import com.zhang.project.biz.enums.OriginEnum;
import com.zhang.project.biz.enums.UserStatusEnum;
import com.zhang.project.biz.util.JwtTokenUtils;
import com.zhang.project.biz.util.RedisUtil;
import com.zhang.project.dal.dao.*;
import com.zhang.project.dal.dataobject.*;
import com.zhang.project.web.form.UserForm;
import com.zhang.project.web.form.UserInfoForm;
import com.zhang.project.web.vo.SideBarVO;
import com.zhang.project.web.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author Distance
 * @since 2021-10-09 14:16:05
 */
@Service("userManager")
public class UserManager {

    @Resource
    private UserDAO userDAO;

    @Resource
    private RoleDAO roleDAO;

    @Resource
    private UserRoleRelationDAO userRoleRelationDAO;

    @Resource
    private DeptManager deptManager;

    @Resource
    private MenuDAO menuDAO;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    private static final long DOUBLE_EXPIRE_TIME = 2 * EXPIRE_TIME;

    /**
     * 用户登录
     *
     * @param form
     * @return
     */
    public JSONObject login(UserForm form) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", form.getUsername());
        User user = userDAO.selectOne(wrapper);
        if (ObjectUtil.isNull(user)) {
            throw new BizCoreException("用户名不存在!");
        }
        if (!StringUtils.equals(form.getPassword(), user.getPassword())) {
            throw new BizCoreException("用户名或密码错误!");
        }
        if (StringUtils.equals(UserStatusEnum.BAN.getCode(), user.getStatus())) {
            throw new BizCoreException("该用户被禁止登录!");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userOpenId",user.getOpenId());
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());
        String accessToken = jwtTokenUtils.createToken(claims,EXPIRE_TIME);
        String refreshToken = jwtTokenUtils.createToken(claims,DOUBLE_EXPIRE_TIME);
        redisUtil.set(user.getOpenId(),accessToken);
        JSONObject object = new JSONObject();
        String roleType = getRoleByUserOpenId(user.getOpenId());
        if(StringUtils.equals(OriginEnum.ADMIN.getCode(),form.getOrigin())){
            List<SideBarVO> sideBarList = getSideBarList(roleType);
            object.put("sideBar",sideBarList);
        }
        object.put("user",coverUserVO(user,roleType));
        object.put("authorization",accessToken);
        object.put("refreshToken",refreshToken);
        return object;
    }

    public Page<UserVO> findUserList(UserForm form){

        Page<User> page = new Page<>(form.getCurrent(),form.getSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(form.getClazz())){
            map.put("clazz",form.getClazz());
        }
        if(StringUtils.isNotBlank(form.getSno())){
            map.put("sno",form.getSno());
        }
        if(StringUtils.isNotBlank(form.getName())){
            map.put("name",form.getName());
        }
        if(StringUtils.isNotBlank(form.getUserOpenIdList())){
            List<String> userOpenIdList = JSONArray.parseArray(form.getUserOpenIdList(), String.class);
            wrapper.in("open_id",userOpenIdList);
        }else{
            wrapper.allEq(map);
        }
        List<User> records = userDAO.selectPage(page, wrapper).getRecords();
        List<UserVO> userVOS = records.stream().map(user -> coverUserVO2(user)).collect(Collectors.toList());
        Page<UserVO> resultPage = new Page<>(form.getCurrent(),form.getSize());
        resultPage.setRecords(userVOS);
        resultPage.setTotal(page.getTotal());
        return resultPage;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createUser(UserForm form){
        User user = new User();
        String openId= IdUtil.fastSimpleUUID();
        user.setOpenId(openId);
        user.setUsername(form.getSno());
        user.setPassword("ngyst"+form.getSno());
        user.setClazz(form.getClazz());
        user.setSno(form.getSno());
        user.setName(form.getName());
        user.setSex(form.getSex());
        user.setPhone(form.getPhone());
        user.setEmail(form.getEmail());
        user.setUserInfo("{}");
        user.setStatus(UserStatusEnum.NORMAL.getCode());
        userDAO.insert(user);
        deptManager.updateDeptByDeptName(form.getDeptName(),openId);
        createUserRoleRelation(openId,form.getRoleType());
    }

    public List<UserVO> getUserInfoList(){
        List<User> userList = userDAO.selectList(null);
        return userList.stream().map(user -> {
            return UserVO.builder().name(user.getName())
                    .clazz(user.getClazz()).avatar(user.getAvatar()).sno(user.getSno())
                    .description("").userInfo(user.getUserInfo()).build();
        }).collect(Collectors.toList());
    }

    private void createUserRoleRelation(String userOpenId,String roleType){

        if(StringUtils.equals("ALL",roleType)){
            Role role = roleDAO.findByType("ADMINISTRATOR");
            if(ObjectUtil.isNull(role)){
                throw new BizCoreException("没有找到该角色类型");
            }
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUserOpenId(userOpenId);
            userRoleRelation.setRoleOpenId(role.getOpenId());
            userRoleRelationDAO.insert(userRoleRelation);

            role = roleDAO.findByType("USER");
            if(ObjectUtil.isNull(role)){
                throw new BizCoreException("没有找到该角色类型");
            }
            userRoleRelation.setRoleOpenId(role.getOpenId());
            userRoleRelationDAO.insert(userRoleRelation);

        }else{
            Role role = roleDAO.findByType(roleType);
            if(ObjectUtil.isNull(role)){
                throw new BizCoreException("没有找到该角色类型");
            }
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUserOpenId(userOpenId);
            userRoleRelation.setRoleOpenId(role.getOpenId());
            userRoleRelationDAO.insert(userRoleRelation);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UserForm form){
        String status = form.getStatus();
        if(StringUtils.equals(UserStatusEnum.NORMAL.getCode(),status)){
            status = UserStatusEnum.BAN.getCode();
        }else{
            status = UserStatusEnum.NORMAL.getCode();
        }

        int update= userDAO.updateStatusByOpenId(form.getOpenId(), status);
        if(update == 0){
            throw new BizCoreException("修改用户状态失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void createUserInfo(UserInfoForm form){

        String userOpenId = form.getUserOpenId();
        User user = userDAO.findByUserOpenId(userOpenId);
        if(user == null){
            throw new BizCoreException("没有此用户");
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        if(StringUtils.isNotBlank(user.getUserInfo())){
            userInfoDTO = JSON.parseObject(user.getUserInfo(), UserInfoDTO.class);
        }
        if(ObjectUtil.isNotNull(form.getUserItem())){
            UserInfoDTO.UserItem userItem = new UserInfoDTO().new UserItem();
            BeanUtils.copyProperties(form.getUserItem(),userItem);
            userInfoDTO.setUserItem(userItem);
        }
        if(ObjectUtil.isNotNull(form.getUserSkill())){
            UserInfoDTO.UserSkill userSkill = new UserInfoDTO().new UserSkill();
            BeanUtils.copyProperties(form.getUserSkill(),userSkill);
            userInfoDTO.setUserSkill(userSkill);
        }
        if(ObjectUtil.isNotNull(form.getUserHonor())){
            UserInfoDTO.UserHonor userHonor = new UserInfoDTO().new UserHonor();
            BeanUtils.copyProperties(form.getUserHonor(),userHonor);
            userInfoDTO.setUserHonor(userHonor);
        }
        int update = userDAO.updateUserInfoByOpenId(userOpenId, JSON.toJSONString(userInfoDTO));
        if(update == 0){
            throw new BizCoreException("添加用户项目经历失败");
        }
    }

    /**
     * 查找用户的第一个角色类型
     *
     * @param userOpenId
     * @return
     */
    private String getRoleByUserOpenId(String userOpenId) {
        String roleOpenId = userRoleRelationDAO.findFirstRoleOpenIdByUserOpenId(userOpenId);
        if (StringUtils.isBlank(roleOpenId)) {
            throw new BizCoreException("没有找到roleOpenId");
        }
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", roleOpenId);
        Role role = roleDAO.selectOne(wrapper);
        if (ObjectUtil.isNull(role)) {
            throw new BizCoreException("没有找到角色信息");
        }
        return role.getType();
    }

    /**
     * 查找用户对应的菜单列表
     * @param
     * @return
     */
    public List<SideBarVO> getSideBarList(String roleType) {

        List<Menu> collect = menuDAO.selectList(null).stream().filter(menu -> JSONArray.parseArray(menu.getRole(), String.class).contains(roleType))
                .collect(Collectors.toList());
        List<SideBarVO> sideBarVOList = collect.stream().map(menu -> coverSideBarVO(menu)).collect(Collectors.toList());
        if (sideBarVOList.isEmpty()) {
            return null;

        }
        List<SideBarVO> voList = new ArrayList<>();
        for (SideBarVO sideBarVO : sideBarVOList) {
            if (ObjectUtil.equal(0, sideBarVO.getParentId())) {
                voList.add(sideBarVO);
            }
        }
        for (SideBarVO sideBarVO : voList) {
            List<SideBarVO> childrenList = getChildrenList(sideBarVO.getId(), sideBarVOList);
            sideBarVO.setChildren(childrenList);
        }
        return voList;
    }

    /**
     * 递归添加子菜单
     * @param id
     * @param menuList
     * @return
     */
    private List<SideBarVO> getChildrenList(Integer id, List<SideBarVO> menuList) {
        List<SideBarVO> childList = new ArrayList<>();
        for (SideBarVO sideBarVO : menuList) {
            if (ObjectUtil.equal(id, sideBarVO.getParentId())) {
                childList.add(sideBarVO);
            }
        }
        //递归
        for (SideBarVO sideBarVO : childList) {
            sideBarVO.setChildren(getChildrenList(sideBarVO.getId(), menuList));
        }
        if (childList.isEmpty()) {
            return new ArrayList<>();
        }
        return childList;
    }

    /**
     * 组装SideBarVO
     * @param menu
     * @return
     */
    private static SideBarVO coverSideBarVO(Menu menu) {
        return SideBarVO.builder()
                .id(menu.getId()).title(menu.getTitle())
                .path(menu.getPath()).icon(menu.getIcon())
                .parentId(menu.getParentId()).build();
    }

    public String findNameByUserOpenId(String openId){
        User user = userDAO.findByUserOpenId(openId);
        if(ObjectUtil.isNull(user)){
            throw new BizCoreException("该用户不存在");
        }
        return user.getName();
    }

    public String findAvatarUserOpenId(String openId){
        User user = userDAO.findByUserOpenId(openId);
        if(ObjectUtil.isNull(user)){
            throw new BizCoreException("该用户不存在");
        }
        return user.getAvatar();
    }

    public void updateAvatarUserOpenId(String openId,String avatar){
        User user = userDAO.findByUserOpenId(openId);
        if(ObjectUtil.isNull(user)){
            throw new BizCoreException("该用户不存在");
        }
        int updateAvatarById = userDAO.updateAvatarById(user.getId(), avatar);
        if(updateAvatarById == 0){
            throw new BizCoreException("修改头像失败");
        }
    }

    private static UserVO coverUserVO(User user,String roleType){
        return UserVO.builder().openId(user.getOpenId())
                .username(user.getUsername()).email(user.getEmail())
                .phone(user.getPhone()).name(user.getName()).avatar(user.getAvatar())
                .sex(user.getSex()).roleType(roleType).userInfo(user.getUserInfo()).build();
    }

    private static UserVO coverUserVO2(User user){
        return UserVO.builder().openId(user.getOpenId())
                .email(user.getEmail()).phone(user.getPhone())
                .name(user.getName()).clazz(user.getClazz()).userInfo(user.getUserInfo())
                .sno(user.getSno()).sex(user.getSex()).status(user.getStatus()).build();
    }

}
