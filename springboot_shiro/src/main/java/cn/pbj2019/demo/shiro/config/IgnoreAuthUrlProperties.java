package cn.pbj2019.demo.shiro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.shiro")
@Data
public class IgnoreAuthUrlProperties {
    List<String> ignoreAuthUrl;
}