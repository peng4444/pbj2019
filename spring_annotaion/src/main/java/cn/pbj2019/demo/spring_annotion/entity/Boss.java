package cn.pbj2019.demo.spring_annotion.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @pClassName: Boss
 * @author: pengbingjiang
 * @create: 2020/6/25 16:16
 * @description: TODO
 */
@Component
public class Boss {

    private Car car;

//    @Autowired
    public Boss(@Autowired Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
