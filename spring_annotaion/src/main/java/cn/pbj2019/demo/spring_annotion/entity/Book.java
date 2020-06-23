package cn.pbj2019.demo.spring_annotion.entity;

/**
 * @ClassName: Book
 * @Author: pbj
 * @Date: 2019/7/22 20:20
 * @Description: TODO
 */
public class Book {

    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
