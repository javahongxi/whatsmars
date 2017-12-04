package com.whatsmars.spring.boot.dao;

import com.whatsmars.spring.boot.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by shenhongxi on 2017/6/26.
 */
@Mapper
public interface UserMapper {

    User findByUsername(String username);
}
