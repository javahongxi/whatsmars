package org.hongxi.whatsmars.spring.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by shenhongxi on 2021/4/2.
 */
@Data
public class User {

    private String name;
    private Integer age;
    private Address address;
    private List<Address> addresses;
}
