package cn.pbj2019.demo.sso_vue.service;


import cn.pbj2019.demo.sso_vue.domain.LoginResultDetails;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2019-12-02 18:50
 */
public interface LoginService {
    /**
     * @return 返回登陆信息
     * */
    LoginResultDetails get();
}
