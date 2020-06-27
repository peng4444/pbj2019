package cn.pbj2019.demo.spring_annotion.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @pClassName: MyBeanFactoryPostProcessor
 * @author: pengbingjiang
 * @create: 2020/6/27 15:36
 * @description: TODO
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor...postProcessor");
        int count = beanFactory.getBeanDefinitionCount();
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("当前BeanFactory中有："+count+"个Bean");
        System.out.println(Arrays.asList(names));
    }
}
