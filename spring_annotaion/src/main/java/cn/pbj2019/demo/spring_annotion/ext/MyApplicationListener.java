package cn.pbj2019.demo.spring_annotion.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @pClassName: MyApplicationListener
 * @author: pengbingjiang
 * @create: 2020/6/27 16:07
 * @description: TODO ApplicationListener
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    //当容器中发布此事件以后，方法触发
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("收到事件："+applicationEvent);
    }
}
