package cn.pbj2019.demo.spring_annotion.config;

import cn.pbj2019.demo.spring_annotion.entity.Color;
import cn.pbj2019.demo.spring_annotion.entity.Person;
import cn.pbj2019.demo.spring_annotion.utils.LinuxCondition;
import cn.pbj2019.demo.spring_annotion.utils.WindowsCondition;
import org.springframework.context.annotation.*;

/**
 * @ClassName: SpringConfig2
 * @Author: pbj
 * @Date: 2019/7/22 21:15
 * @Description: TODO
 */
//@Conditional(WindowsCondition.class) //类中组件统一配置 ，满足当前条件，这个类中配置的所有的bean注册才能生效
@Configuration
@Import({Color.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})//给容器导入组件，id默认为组件的全类名 @Import({Color.class,Red.class}) 导入多个
public class SpringConfig2 {

    //默认是单实例的
    /* *
     * 功能描述:Scope
     * prototype:多实例的 :IOC容器启动并不会去调用方法创建对象到容器中，每次获取的时候才会调用方法创建对象
     * singleton : 单实例（默认值) :IOC容器启动的时候会调用方法创建对象放到IOC容器中，以后每次就是直接从容器（map.get()）中拿
     * request :同一次请求创建一个实例
     * session：同一个session创建一个实例
     * @param: []
     * @return: cn.pbj2019.demo.spring_annotion.entity.Person
     * @auther: pbj
     * @date: 2019/7/22 21:20
     *  @Scope :调整作用域
     *
     * 懒加载：单实例bean：默认在容器启动的时候创建对象
     *         懒加载：容器启动不创建对象，第一次使用（获取）Bean创建对象，并初始化
     */
//    @Scope("prototype")
    @Lazy  //懒加载注解
    @Scope
    @Bean("person")
    public Person person(){
        System.out.println("给容器中添加Person");
        return new Person("aaa", 23);
    }


    //@Conditional({Condition}):按照一定的条件进行判断，满足条件给容器注册bean
    //如果系统是windows,给容器中注册bill，如果系统是Linux，给容器注册jobs
    @Conditional({WindowsCondition.class}) //还可以放在类上
    @Bean("bill")
    public Person person01(){
        return new Person("bill", 62);
    }

    @Conditional({LinuxCondition.class})
    @Bean("jobs")
    public Person person02(){
        return new Person("jobs", 55);
    }

    /**
     * 给容器中注册组件；
     * 1）、包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）[自己写的类]
     * 2）、@Bean[导入的第三方包里面的组件]
     * 3）、@Import[快速给容器中导入一个组件]
     * 		1）、@Import(要导入到容器中的组件)；容器中就会自动注册这个组件，id默认是全类名
     * 		2）、ImportSelector:返回需要导入的组件的全类名数组；
     * 		3）、ImportBeanDefinitionRegistrar:手动注册bean到容器中
     * 4）、使用Spring提供的 FactoryBean（工厂Bean）;
     * 		1）、默认获取到的是工厂bean调用getObject创建的对象
     * 		2）、要获取工厂Bean本身，我们需要给id前面加一个&
     * 			&colorFactoryBean
     */
    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }

}
