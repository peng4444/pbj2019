package cn.pbj2019.demo.springboot_shiro.config;

import cn.pbj2019.demo.springboot_shiro.entity.User;
import cn.pbj2019.demo.springboot_shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: UserRealm
 * @Author: pbj
 * @Date: 2019/9/4 00:05
 * @Description: TODO 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userSerivce;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println("执行授权逻辑");

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //添加资源的授权字符串
        info.addStringPermission("user:add");

        return info;

    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //假设数据库的用户名和密码
//        String name = "eric";
//        String password = "123456";

        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken)arg0;
        User user = userSerivce.findByName(token.getUsername());
        if(user==null){
            //用户名不存在
            return null;//shiro底层会抛出UnKnowAccountException
        }

        //2.判断密码
        return new SimpleAuthenticationInfo("",user.getPassword(),"");
    }
}

