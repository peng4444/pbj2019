package cn.pbj2019.demo.spring_annotion.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @ClassName: UserDao
 * @Author: pbj
 * @Date: 2019/10/2 16:50
 * @Description: TODO
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void insert() {
        String sql = "insert into t_user (name,password,age) values(?,?,?)";

        String username = UUID.randomUUID().toString().substring(0, 5);
        jdbcTemplate.update(sql, username, "123456",18);
    }
}
