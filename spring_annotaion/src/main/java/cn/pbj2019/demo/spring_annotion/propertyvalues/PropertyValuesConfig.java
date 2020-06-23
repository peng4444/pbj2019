package cn.pbj2019.demo.spring_annotion.propertyvalues;

import cn.pbj2019.demo.spring_annotion.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: PropertyValuesConfig
 * @Author: pbj
 * @Date: 2019/7/25 23:01
 * @Description: TODO
 */
//使用@PropertySource读取外部配置文件的中保存的k/v保存到运行环境中,使用#{}取出配置文件中的值
@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class PropertyValuesConfig {

    @Bean
    public Person person(){
        return new Person();
    }
}
