package cn.pbj2019.demo.shiro.sevice;


import cn.pbj2019.demo.shiro.entity.UserInfo;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    UserInfo findByUsername(String username);
}