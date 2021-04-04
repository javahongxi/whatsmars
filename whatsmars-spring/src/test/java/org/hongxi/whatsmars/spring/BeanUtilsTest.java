package org.hongxi.whatsmars.spring;

import org.hongxi.whatsmars.spring.bean.Address;
import org.hongxi.whatsmars.spring.bean.Student;
import org.hongxi.whatsmars.spring.bean.User;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhongxi on 2021/4/2.
 */
public class BeanUtilsTest {

    @Test
    public void t() {
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setEmail("lily@qq.com");
        address.setHome("Beijing");
        addresses.add(address);

        User user = new User();
        user.setName("lily");
        user.setAge(24);
        user.setAddress(address);
        user.setAddresses(addresses);

        Student student = new Student();
        BeanUtils.copyProperties(user, student);
        System.out.println(student);
    }
}
