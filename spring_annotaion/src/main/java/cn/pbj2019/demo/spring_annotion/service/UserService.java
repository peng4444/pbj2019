package cn.pbj2019.demo.spring_annotion.service;

import cn.pbj2019.demo.spring_annotion.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: UserService
 * @Author: pbj
 * @Date: 2019/10/2 16:53
 * @Description: TODO
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional//事务方法注解并且配置类中添加@EnableTransactionManagement开启事务管理功能。同时配置事务管理器。
    public void insertUser() {
        userDao.insert();
        System.out.println("数据库插入成功");
         int i  = 10/0;
    }
}
