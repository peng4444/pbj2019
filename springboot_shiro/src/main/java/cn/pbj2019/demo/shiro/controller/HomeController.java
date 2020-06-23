package cn.pbj2019.demo.shiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {
    @RequestMapping({"/", "/index"})
    public String index() {
        return "/index";
    }

    @RequestMapping("/test")
    public String test() {
        return "/test";
    }
}