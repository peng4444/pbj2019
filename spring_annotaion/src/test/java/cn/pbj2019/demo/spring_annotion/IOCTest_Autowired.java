package cn.pbj2019.demo.spring_annotion;

import cn.pbj2019.demo.spring_annotion.config.AutowiredConfig;
import cn.pbj2019.demo.spring_annotion.dao.BookDao;
import cn.pbj2019.demo.spring_annotion.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: IOCTest_Autowired
 * @Author: pbj
 * @Date: 2019/7/31 17:25
 * @Description: TODO
 */
public class IOCTest_Autowired {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutowiredConfig.class);

        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);

        BookDao bookDao = applicationContext.getBean(BookDao.class);
        System.out.println(bookDao);

        System.out.println(applicationContext);

        applicationContext.close();
    }
}
