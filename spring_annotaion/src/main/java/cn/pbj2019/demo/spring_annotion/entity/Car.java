package cn.pbj2019.demo.spring_annotion.entity;

/**
 * @ClassName: Car
 * @Author: pbj
 * @Date: 2019/7/25 22:07
 * @Description: TODO
 */
public class Car {

    public Car(){
        System.out.println("car constructor......");
    }

    public void init(){
        System.out.println("car .............init......");
    }

    public void destroy(){
        System.out.println("car ..............destroy....");
    }
}
