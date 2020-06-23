package cn.pbj2019.demo.sso_vue.config;

import cn.pbj2019.demo.sso_vue.util.VerifyCodeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebVerifyCodeConfig {
    @Bean
    public VerifyCodeUtil verifyCodeUtil() {
        return new VerifyCodeUtil();
    }
}
