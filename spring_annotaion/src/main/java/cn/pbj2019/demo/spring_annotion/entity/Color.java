package cn.pbj2019.demo.spring_annotion.entity;

/**
 * @ClassName: Color
 * @Author: pbj
 * @Date: 2019/7/23 23:49
 * @Description: TODO
 */
public class Color {

    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color{" +
                "car=" + car +
                '}';
    }
}
