package cn.pbj2019.demo.spring_annotion.servlet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @pClassName: AppConfig
 * @author: pengbingjiang
 * @create: 2020/6/27 20:12
 * @description: TODO
 */
@Configuration
@ComponentScan(value = "cn.pbj2019.demo.spring_annotion.servlet",includeFilters = {
      @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
},useDefaultFilters = false)
public class AppConfig {
}
