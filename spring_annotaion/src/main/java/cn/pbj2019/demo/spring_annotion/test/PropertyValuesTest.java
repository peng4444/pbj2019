package cn.pbj2019.demo.spring_annotion.test;

import cn.pbj2019.demo.spring_annotion.entity.Person;
import cn.pbj2019.demo.spring_annotion.propertyvalues.PropertyValuesConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @ClassName: PropertyValuesTest
 * @Author: pbj
 * @Date: 2019/7/25 23:06
 * @Description: TODO 属性赋值测试
 */
@Configuration
public class PropertyValuesTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertyValuesConfig.class);

    @Test
    public void test01(){
        printBeans(applicationContext);
        System.out.println("==================");

        Person person = (Person)applicationContext.getBean("person");
        System.out.println(person);
        //获取配置文件中的赋值
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String properties = environment.getProperty("person.age");
        System.out.println(properties);
        System.out.println("-----------------");
    }

    //复杂beans输出
    public void printBeans( AnnotationConfigApplicationContext applicationContext){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beans : beanDefinitionNames) {
            System.out.println(beans);
        }
    }
}
