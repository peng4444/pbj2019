package cn.pbj2019.demo.spring_annotion.dao;

import org.springframework.stereotype.Repository;

/**
 * @ClassName: BookDao
 * @Author: pbj
 * @Date: 2019/7/22 20:24
 * @Description: TODO
 */
@Repository
public class BookDao {

    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
