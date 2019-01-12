package org.hongxi.whatsmars.boot.sample.mybatis.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * Created by shenhongxi on 2017/6/26.
 */
@Data
@Builder
public class User {
    private Long id;

    private String username;

    private String nickname;

    private Integer gender;

    private Integer age;

    private Date createDate;

    private Date updateDate;

    @Tolerate
    public User() {}

}
