package org.hongxi.whatsmars.spring.boot.dao;

import org.hongxi.whatsmars.spring.boot.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by shenhongxi on 2018/2/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        assert userMapper != null;
        userMapper.createIfNotExistsTable();
        User user = new User();
        user.setUsername("javahongxi");
        user.setNickname("hongxi");
        user.setGender(1);
        user.setAge(28);
        userMapper.insert(user);
    }
}
