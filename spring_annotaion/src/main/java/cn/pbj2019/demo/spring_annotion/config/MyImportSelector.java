package cn.pbj2019.demo.spring_annotion.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName: MyImportSelector
 * @Author: pbj
 * @Date: 2019/7/24 19:32
 * @Description: TODO
 */
//自定义逻辑返回需要导入的组件
public class MyImportSelector implements ImportSelector {

    //返回值，就是要导入到容器中的组件的去哪类名
    //annotationMetadata:当前标注@Import注解的类的所有的注解信息
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //方法不要返回null
//        annotationMetadata
        return new String[]{"cn.pbj2019.demo.spring_annotion.entity.Red","cn.pbj2019.demo.spring_annotion.entity.Color"}; //需要返回的对象的全类名数组
    }
}
