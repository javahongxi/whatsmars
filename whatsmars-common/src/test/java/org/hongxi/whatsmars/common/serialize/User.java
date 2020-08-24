package org.hongxi.whatsmars.common.serialize;

import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * @author shenhongxi 2019/8/5
 */
@Message
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7723128823885218090L;

    String name;
    int age;

    public User() {}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

}