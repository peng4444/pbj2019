package cn.pbj2019.demo.spring_annotion.config;

import cn.pbj2019.demo.spring_annotion.entity.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName: MyImportBeanDefinitionRegistrar
 * @Author: pbj
 * @Date: 2019/7/24 19:48
 * @Description: TODO
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    //annotationMetadata 当前标注@Import注解的类的所有的注解信息
    //beanDefinitionRegistry  :BeanDefinition注册类 把所有的需要添加到容器中的bean注册进来
    // 调用beanDefinitionRegistry.registerBeanDefinition 手动注册
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean red = beanDefinitionRegistry.containsBeanDefinition("cn.pbj2019.demo.spring_annotion.entity.Red");
        boolean color = beanDefinitionRegistry.containsBeanDefinition("cn.pbj2019.demo.spring_annotion.entity.Color");
        if (red && color) {
            //指定bean定义信息
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            //注册一个bean，指定bean名
            beanDefinitionRegistry.registerBeanDefinition("rainBow", rootBeanDefinition);//手动注入
        }
    }
}
