package cn.pbj2019.demo.spring_annotion.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName: Dog
 * @Author: pbj
 * @Date: 2019/7/25 22:24
 * @Description: TODO
 */
@Component
public class Dog {

    public Dog() {
        System.out.println("dog .. constructor...");
    }

    @PostConstruct
    public void init(){
        System.out.println("Dog @PostConstruct：在bean创建完成并且属性赋值完成；来执行初始化方法");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Dog @PreDestroy：在容器销毁bean之前通知我们进行清理工作");
    }
}
