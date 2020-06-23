package cn.pbj2019.demo.springboot_shiro.service.impl;

import cn.pbj2019.demo.springboot_shiro.dao.UserMapper;
import cn.pbj2019.demo.springboot_shiro.entity.User;
import cn.pbj2019.demo.springboot_shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: UserServiceImpl
 * @Author: pbj
 * @Date: 2019/9/4 17:09
 * @Description: TODO
 */
public class UserServiceImpl implements UserService {
    //注入Mapper接口
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

}
