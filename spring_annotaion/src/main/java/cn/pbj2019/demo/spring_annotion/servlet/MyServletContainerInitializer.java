package cn.pbj2019.demo.spring_annotion.servlet;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * @pClassName: MyServletContainerInitializer
 * @author: pengbingjiang
 * @create: 2020/6/27 19:57
 * @description: TODO 容器启动的时候会将@HandlesTypes指定的这个类型下面的子类（实现类，子接口）传递过来给Set<Class<?>>参数
 */
@HandlesTypes(WebApplicationInitializer.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {
    /**
     * 应用启动的时候，会运行onStartup方法
     * ServletContext　代表当前web应用的ServletContext，一个Web应用对应一个
     * Set<Class<?>> 感兴趣类型的所有子类型
     *      可使用ServletContext注册Web组件（Servlet、Filter、Listener）
            *      使用编码的方式，再项目启动的时候给ServletContext里面添加组件
     *          必须在项目启动的时候添加
     *          1）ServletContainerInitializer得到的ServletContext
     *          2）ServletContextListener得到的ServletContext
     */
    @Override
    public void onStartup(Set<Class<?>> args0, ServletContext sc){
        System.out.println("感兴趣的类型：");
        for (Class<?> claz : args0) {
            System.out.println(claz);
        }
    }
}
