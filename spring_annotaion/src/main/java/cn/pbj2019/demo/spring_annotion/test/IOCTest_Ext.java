package cn.pbj2019.demo.spring_annotion.test;

import cn.pbj2019.demo.spring_annotion.config.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @pClassName: IOCTest_Ext
 * @author: pengbingjiang
 * @create: 2020/6/27 15:37
 * @description: TODO 扩展类测试
 */
public class IOCTest_Ext {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        //发布事件
        applicationContext.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
        });

        applicationContext.close();
    }
}
