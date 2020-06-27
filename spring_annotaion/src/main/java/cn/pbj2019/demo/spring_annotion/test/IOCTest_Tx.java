package cn.pbj2019.demo.spring_annotion.test;

import cn.pbj2019.demo.spring_annotion.config.TxConfig;
import cn.pbj2019.demo.spring_annotion.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: IOCTest_Tx
 * @Author: pbj
 * @Date: 2019/7/31 17:25
 * @Description: TODO 事务测试类
 */
public class IOCTest_Tx {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);

        userService.insertUser();

        applicationContext.close();
    }
}
