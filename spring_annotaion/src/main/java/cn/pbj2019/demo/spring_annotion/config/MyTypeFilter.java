package cn.pbj2019.demo.spring_annotion.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @ClassName: MyTypeFilter
 * @Author: pbj
 * @Date: 2019/7/22 21:06
 * @Description: TODO 自定义包扫描规则
 */
public class MyTypeFilter implements TypeFilter {

    /* *
     * 功能描述: 
     * @param: [metadataReader 读取到的当前正在扫描的类的信息, metadataReaderFactory 可以获取到其他任何类的信息]
     * @return: boolean
     * @auther: pbj
     * @date: 2019/7/22 21:08
     */
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取到当前类注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类资源（类的路径)
        Resource resource = metadataReader.getResource();
        String className = classMetadata.getClassName();
        System.out.println("--->" + className);
        if (className.contains("er")) { //添加过滤规则
            return true;
        }
        return false;
    }
}
