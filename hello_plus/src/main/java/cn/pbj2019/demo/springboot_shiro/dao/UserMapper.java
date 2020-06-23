package cn.pbj2019.demo.springboot_shiro.dao;

import cn.pbj2019.demo.springboot_shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: UserMapper
 * @Author: pbj
 * @Date: 2019/9/4 17:06
 * @Description: TODO
 */
@Mapper
public interface UserMapper {
    public User findByName(String name);
}
