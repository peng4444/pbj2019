package cn.pbj2019.demo.spring_annotion.config;

import cn.pbj2019.demo.spring_annotion.entity.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @pClassName: ExtConfig
 * @author: pengbingjiang
 * @create: 2020/6/27 15:35
 * @description: TODO 扩展类配置
 */
@ComponentScan("cn.pbj2019.demo.spring_annotion.ext")
@Configuration
public class ExtConfig {

    @Bean
    public Car car() {
        return new Car();
    }

}
