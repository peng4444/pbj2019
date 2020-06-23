package cn.pbj2019.demo.springboot_shiro.service;

import cn.pbj2019.demo.springboot_shiro.entity.User;

/**
 * @ClassName: UserService
 * @Author: pbj
 * @Date: 2019/9/4 17:08
 * @Description: TODO
 */
public interface UserService {
    public User findByName(String name);
}
