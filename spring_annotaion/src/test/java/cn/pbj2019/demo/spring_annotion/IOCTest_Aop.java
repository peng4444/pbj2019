package cn.pbj2019.demo.spring_annotion;

import cn.pbj2019.demo.spring_annotion.aop.MathCalculator;
import cn.pbj2019.demo.spring_annotion.config.AopConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: IOCTest_Aop
 * @Author: pbj
 * @Date: 2019/7/31 17:25
 * @Description: TODO
 */
public class IOCTest_Aop {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);

        // 1.不要自己创建对象
//        MathCalculator mathCalculator = new MathCalculator();
//        mathCalculator.div(1, 1);

        //使用spring创建的对象，才能使用aop
        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        mathCalculator.div(1, 1);


        applicationContext.close();
    }
}
