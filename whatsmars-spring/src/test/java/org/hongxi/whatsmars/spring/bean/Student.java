package org.hongxi.whatsmars.spring.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by shenhongxi on 2021/4/2.
 */
@Data
public class Student {

    private String name;
    private Integer age;
    private Contacts address;
    private List<Contacts> addresses;
}
