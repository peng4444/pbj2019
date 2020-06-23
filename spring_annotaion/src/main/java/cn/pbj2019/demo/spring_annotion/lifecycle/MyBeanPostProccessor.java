package cn.pbj2019.demo.spring_annotion.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyBeanPostProccessor
 * @Author: pbj
 * @Date: 2019/7/25 22:31
 * @Description: TODO后置处理器：初始化之前进行处理工作
 */
@Component // 将后置处理器加入到容器中
public class MyBeanPostProccessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization"+bean.toString());
        return null;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization"+bean.toString());
        return null;
    }
}
