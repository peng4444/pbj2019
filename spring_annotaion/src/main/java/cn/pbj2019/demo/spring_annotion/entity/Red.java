package cn.pbj2019.demo.spring_annotion.entity;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @ClassName: Red
 * @Author: pbj
 * @Date: 2019/7/24 19:31
 * @Description: TODO
 */
@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        System.out.println("传入的IOC"+applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("当前bean的名字："+s);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        String s = stringValueResolver.resolveStringValue("你好${os.name}");
        System.out.println("解析的字符串值："+s);
    }
}
