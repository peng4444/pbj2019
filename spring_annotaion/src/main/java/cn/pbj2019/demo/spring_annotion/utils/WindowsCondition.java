package cn.pbj2019.demo.spring_annotion.utils;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @ClassName: WindowsCondition
 * @Author: pbj
 * @Date: 2019/7/23 23:32
 * @Description: TODO
 */
public class WindowsCondition implements Condition {
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String property = environment.getProperty("os.name");


        if (property.contains("Windows")) {
            return true;
        }
        return false;
    }
}
