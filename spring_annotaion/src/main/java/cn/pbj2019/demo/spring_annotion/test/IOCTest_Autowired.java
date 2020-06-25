package cn.pbj2019.demo.spring_annotion.test;

import cn.pbj2019.demo.spring_annotion.config.AutowiredConfig;
import cn.pbj2019.demo.spring_annotion.dao.BookDao;
import cn.pbj2019.demo.spring_annotion.entity.Boss;
import cn.pbj2019.demo.spring_annotion.entity.Car;
import cn.pbj2019.demo.spring_annotion.entity.Color;
import cn.pbj2019.demo.spring_annotion.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: IOCTest_Autowired
 * @Author: pbj
 * @Date: 2019/7/31 17:25
 * @Description: TODO 自动装配测试类
 */
public class IOCTest_Autowired {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AutowiredConfig.class);

        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);

//        BookDao bookDao = applicationContext.getBean(BookDao.class);
//        System.out.println(bookDao);

//        System.out.println(applicationContext);

        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);
        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);

        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);

        applicationContext.close();
    }
}
