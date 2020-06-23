package com.pbj2019.damo.common.entity;

import com.alibaba.fastjson.JSONArray;

/**
 * @ClassName: Position
 * @Author: pbj
 * @Date: 2019/9/10 15:06
 * @Description: TODO
 */
public class Position {
    private Integer value;
    private Integer parent;
    private String label;
    private JSONArray children;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public JSONArray getChildren() {
        return children;
    }

    public void setChildren(JSONArray children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Position{" +
                "value=" + value +
                ", parent=" + parent +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
