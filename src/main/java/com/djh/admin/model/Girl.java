package com.djh.admin.model;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/24.
 */
@Component
public class Girl {
    private String name;
    private Integer age;

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
}
