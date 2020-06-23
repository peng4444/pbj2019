package cn.pbj2019.demo.spring_annotion.config;

import cn.pbj2019.demo.spring_annotion.entity.Color;
import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName: ColorFactoryBean
 * @Author: pbj
 * @Date: 2019/7/24 19:58
 * @Description: TODO
 */
public class ColorFactoryBean implements FactoryBean<Color> {

    //返回一个Color对象，这个对象会返回到容器中
    public Color getObject() throws Exception {
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }

    //是一个单例
    public boolean isSingleton() {
        return true;
    }
}
