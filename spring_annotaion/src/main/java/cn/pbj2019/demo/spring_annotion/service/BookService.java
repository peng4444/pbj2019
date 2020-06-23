package cn.pbj2019.demo.spring_annotion.service;

import cn.pbj2019.demo.spring_annotion.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: BookService
 * @Author: pbj
 * @Date: 2019/7/22 20:24
 * @Description: TODO
 */
@Service
public class BookService {
    @Autowired
    BookDao bookDao;

    public void print(){
        System.out.println(bookDao);
    }
}
