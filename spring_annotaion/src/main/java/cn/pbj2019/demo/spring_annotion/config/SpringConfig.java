package cn.pbj2019.demo.spring_annotion.config;

import cn.pbj2019.demo.spring_annotion.entity.Person;
import cn.pbj2019.demo.spring_annotion.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SpringConfig
 * @Author: pbj
 * @Date: 2019/7/21 23:46
 * @Description: TODO 配置类 = 配置文件bean.xml
 */
@Configuration //告诉Spring这是一个配置类
//相当于配置文件中的<context:component-scan base-package="cn.pbj2019.demo.spring_annotion"></context:component-scan>
@ComponentScan(value = "cn.pbj2019.demo.spring_annotion",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {BookService.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class})
})
//excludeFilters 扫描的时候指定不包含
//includeFilters 扫描的时候指定包含 includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})},useDefaultFilters = false
//@ComponentScans可以多指定多个ComponentScan规则
//FilterType.ANNOTATION :按照注释
//FilterType.ASSIGNABLE_TYPE :按照给定的类型
//FilterType.ASPECTJ :使用ASPECTJ表达式
//FilterType.REGEX :使用正则表达式
//FilterType.CUSTOM :使用自定义的过滤规则
public class SpringConfig {

    //从容器中注册一个Bean，类型为返回值的类型，id默认为用方法名作为id
    @Bean //@Bean("personId")可以指定id为personId
    public Person person(){//方法名为默认的id
       return new Person("pbj", 23);
    }
}
