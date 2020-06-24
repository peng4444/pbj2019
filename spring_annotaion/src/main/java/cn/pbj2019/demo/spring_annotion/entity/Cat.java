package cn.pbj2019.demo.spring_annotion.entity;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Cat
 * @Author: pbj
 * @Date: 2019/7/25 22:16
 * @Description: TODO
 */
@Component
public class Cat implements InitializingBean, DisposableBean {

    public Cat(){
        System.out.println("cat .......constructor.........");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat .........after propertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("cat destroy.........");
    }


}
