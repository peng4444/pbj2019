package cn.pbj2019.demo.shiro.sevice.impl;


import cn.pbj2019.demo.shiro.entity.UserInfo;
import cn.pbj2019.demo.shiro.dao.UserInfoDao;
import cn.pbj2019.demo.shiro.sevice.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}