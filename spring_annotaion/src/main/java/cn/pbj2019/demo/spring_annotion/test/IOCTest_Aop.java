package cn.pbj2019.demo.spring_annotion.test;

import cn.pbj2019.demo.spring_annotion.aop.MathCalculator;
import cn.pbj2019.demo.spring_annotion.config.AopConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: IOCTest_Aop
 * @Author: pbj
 * @Date: 2019/7/31 17:25
 * @Description: TODO Spring AOP测试类
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
//输出
//div运行。。。@Before:参数列表是：{[1, 1]}
//正在运行除法运算。。。。。
//div结束。。。@After
//div正常返回。。。@AfterReturning:运行结果：{1}

        applicationContext.close();
    }
}
