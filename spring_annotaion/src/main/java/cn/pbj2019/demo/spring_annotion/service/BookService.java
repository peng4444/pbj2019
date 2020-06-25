package cn.pbj2019.demo.spring_annotion.service;

import cn.pbj2019.demo.spring_annotion.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @ClassName: BookService
 * @Author: pbj
 * @Date: 2019/7/22 20:24
 * @Description: TODO
 */
@Service
public class BookService {

    //@Qualifier("bookDao") //指定需要装配的组件的id，而不是使用属性名
    //@Resource(name="bookDao2") //类似于@Autowired一样实现自动装配，默认是按照组件名称进行装配
    //@Inject
    @Autowired
    private BookDao bookDao;

    public void print(){
        System.out.println(bookDao);
    }
}
