package cn.pbj2019.demo.spring_annotion.controller;

import cn.pbj2019.demo.spring_annotion.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @ClassName: BookController
 * @Author: pbj
 * @Date: 2019/7/22 20:23
 * @Description: TODO
 */
@Controller
public class BookController {

    @Autowired
    BookService bookService;
}
