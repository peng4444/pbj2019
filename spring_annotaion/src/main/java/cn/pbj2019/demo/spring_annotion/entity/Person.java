package cn.pbj2019.demo.spring_annotion.entity;

import org.springframework.beans.factory.annotation.Value;

/**
 * @ClassName: Person
 * @Author: pbj
 * @Date: 2019/7/21 23:36
 * @Description: TODO
 */
public class Person {

    //使用@Value赋值
    //1.基本数值
    //2.可以SpEL;#{}
    //3.可以写${},取出配置文件【person.properties】中的值（运行在环境变量里面的值）
    @Value("123")
    private String name;
    @Value("#{20-2}")  //@Value("${person.age}")
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
