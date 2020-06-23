package cn.pbj2019.demo.shiro.dao;

import cn.pbj2019.demo.shiro.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoDao extends CrudRepository<UserInfo, Long> {
    /**
     * 通过username查找用户信息;
     */
    UserInfo findByUsername(String username);
}