package cn.pbj2019.demo.spring_annotion.utils;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @ClassName: LinuxCondition
 * @Author: pbj
 * @Date: 2019/7/23 23:33
 * @Description: TODO
 */
public class LinuxCondition implements Condition {
    /* *
     * 功能描述: 
     * @param: [conditionContext 判断条件能够使用的上下文环境, annotatedTypeMetadata  注释信息]
     * @return: boolean
     * @auther: pbj
     * @date: 2019/7/23 23:34
     */
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //判断是否是Linux系统
        //1.获取到IOC使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //2.获取到类加载器
        conditionContext.getClassLoader();
        //3.获取到当前环境
        Environment environment = conditionContext.getEnvironment();
        //4.获取到bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        //可以判断容器中的bean的注册情况，也可以给容器注册bean
        boolean person = registry.containsBeanDefinition("person");

        String property = environment.getProperty("os.name");
        if (property.contains("linux")) {
            return true;
        }
        return false;
    }
}
