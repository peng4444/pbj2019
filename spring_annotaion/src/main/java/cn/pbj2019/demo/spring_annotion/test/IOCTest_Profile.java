package cn.pbj2019.demo.spring_annotion.test;

import cn.pbj2019.demo.spring_annotion.config.ProfileConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @pClassName: IOCTest_Profile
 * @author: pengbingjiang
 * @create: 2020/6/25 16:58
 * @description: TODO
 */
public class IOCTest_Profile {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProfileConfig.class);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String string : beanNamesForType) {
            System.out.println(string);
        }
        applicationContext.close();
    }

    public void test02() {
//        1、创建一个applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        2、设置需要激活的环境，applicationContext.getEnvironment().setActiveProfiles("");
        applicationContext.getEnvironment().setActiveProfiles("dev");
//        3、注册主配置类，applicationContext.register(xxx.class)
        applicationContext.register(ProfileConfig.class);
//        4、启动刷新容器，applicationContext.refresh();
        applicationContext.refresh();

    }
}
