package cn.pbj2019.demo.spring_annotion;

import cn.pbj2019.demo.spring_annotion.lifecycle.ConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: LifeCycleTest
 * @Author: pbj
 * @Date: 2019/7/25 22:08
 * @Description: TODO
 */
public class LifeCycleTest {

    @Test
    public void test01() {
        //1.创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigOfLifeCycle.class);
        System.out.println("容器创建完成。。。");

        //2.关闭容器
        applicationContext.close();
    }
}
