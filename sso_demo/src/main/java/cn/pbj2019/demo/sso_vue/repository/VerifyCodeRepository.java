package cn.pbj2019.demo.sso_vue.repository;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2019-11-26 17:07
 */
public interface VerifyCodeRepository {

    void save(String key, String code);

    String find(String key);

    void remove(String key);
}
