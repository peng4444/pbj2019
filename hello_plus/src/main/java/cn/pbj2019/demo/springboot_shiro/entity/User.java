package cn.pbj2019.demo.springboot_shiro.entity;

/**
 * @ClassName: User
 * @Author: pbj
 * @Date: 2019/9/4 17:05
 * @Description: TODO
 */
public class User {
    private Integer id;
    private String name;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
