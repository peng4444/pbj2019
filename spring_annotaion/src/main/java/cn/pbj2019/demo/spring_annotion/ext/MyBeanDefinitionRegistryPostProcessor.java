package cn.pbj2019.demo.spring_annotion.ext;

import cn.pbj2019.demo.spring_annotion.entity.Car;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @pClassName: MyBeanDefinitionRegistryPostProcessor
 * @author: pengbingjiang
 * @create: 2020/6/27 15:51
 * @description: TODO
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry...Bean的数量：+"+beanDefinitionRegistry.getBeanDefinitionCount()+"个Bean");
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Car.class);
//        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Car.class);
        beanDefinitionRegistry.registerBeanDefinition("hello",beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory...Bean的数量："+configurableListableBeanFactory.getBeanDefinitionCount()+"个Bean");
    }
}
