package cn.pbj2019.demo.spring_annotion.test;

import cn.pbj2019.demo.spring_annotion.config.SpringConfig;
import cn.pbj2019.demo.spring_annotion.config.SpringConfig2;
import cn.pbj2019.demo.spring_annotion.entity.Person;
import cn.pbj2019.demo.spring_annotion.entity.Red;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * @pClassName: MainTest
 * @author: pengbingjiang
 * @create: 2020/6/24 17:10
 * @description: TODO 组件注册测试文件
 */
public class MainTest {

    @Test //1.测试Spring通过xml配置文件给实体赋值
    public void Test1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);//输出：Person{name='pbj', age=22}
    }

    @Test //2.测试配置类SpringConfig.java实现实体值注入
    public void TestSpringConfig() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        Person person = annotationConfigApplicationContext.getBean(Person.class);
        System.out.println(person);//输出 Person{name='123', age=18}
        //查看配置类中bean的id
        String[] beanNamesForType = annotationConfigApplicationContext.getBeanNamesForType(Person.class);
        for (String string : beanNamesForType) {
            System.out.println(string);//输出：person为配置类中bean的id,默认为方法名。
        }
    }

    @Test //3.测试包扫描注解，包括spring配置类和spring配置文件
    public void TestSpringMVC() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(SpringConfig.class);
        String[] definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String s : definitionNames) {
            System.out.println(s);//输出容器的所有的
            //org.springframework.context.annotation.internalConfigurationAnnotationProcessor
            //org.springframework.context.annotation.internalAutowiredAnnotationProcessor
            //org.springframework.context.annotation.internalCommonAnnotationProcessor
            //org.springframework.context.event.internalEventListenerProcessor
            //org.springframework.context.event.internalEventListenerFactory
            //springConfig
            //bookController
            //bookDao
            //bookService
            //person
        }
    }

    @Test //4.测试Spring的容器创建和加载对象的过程，单实例(@Scope,默认)还是多实例(@Scope("prototype"))，单实例的懒加载(@Lazy)
    public void TestSpringScope() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(SpringConfig2.class);
//        String[] definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
//        for (String s : definitionNames) {
//            System.out.println(s);
//        }
        System.out.println("IOC容器创建了吗？");
        Object person1 = annotationConfigApplicationContext.getBean("person");
        Object person2 = annotationConfigApplicationContext.getBean("person");
        System.out.println(person1==person2);// @Scope true 默认是单实例的   @Scope("protoype") false 指定为多实例的
    }

    @Test //4.测试按照条件给容器注册bean
    public void TestConditional(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(SpringConfig2.class);
        String [] person =  annotationConfigApplicationContext.getBeanNamesForType(Person.class);
        //获取系统运行环境
        ConfigurableEnvironment environment = annotationConfigApplicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);

        for (String s : person) {
            System.out.println(s);//输出bean名称
        }

        Map<String, Person> beansOfType = annotationConfigApplicationContext.getBeansOfType(Person.class);
        System.out.println(beansOfType);//输出bean的内容
    }

    //复杂beans输出
    public void printBeans( AnnotationConfigApplicationContext annotationConfigApplicationContext){
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String beans : beanDefinitionNames) {
            System.out.println(beans);
        }
    }

    //输出所有的组件
    @Test
    public void testImport(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(SpringConfig2.class);
        printBeans(annotationConfigApplicationContext);
        Red bean = annotationConfigApplicationContext.getBean(Red.class);//MyImportSelector.class导入
        System.out.println(bean);

        //工厂bean获取的是调用getObject创建的对象 Color对象
        Object colorFactoryBean = annotationConfigApplicationContext.getBean("colorFactoryBean");
        Object colorFactoryBean2 = annotationConfigApplicationContext.getBean("colorFactoryBean");
        System.out.println(colorFactoryBean.getClass());
        System.out.println(colorFactoryBean == colorFactoryBean2); //单实例

        Object colorFactoryBean3 = annotationConfigApplicationContext.getBean("&colorFactoryBean");
        System.out.println(colorFactoryBean3.getClass());//& 获取工厂bean的本身
    }
}
