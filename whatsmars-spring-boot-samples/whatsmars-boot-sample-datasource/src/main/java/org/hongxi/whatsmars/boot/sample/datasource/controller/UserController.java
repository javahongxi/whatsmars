package org.hongxi.whatsmars.boot.sample.datasource.controller;

import com.github.pagehelper.Page;
import org.hongxi.whatsmars.boot.sample.datasource.common.ReturnItemUtils;
import org.hongxi.whatsmars.boot.sample.datasource.common.pojo.ReturnItems;
import org.hongxi.whatsmars.boot.sample.datasource.model.User;
import org.hongxi.whatsmars.boot.sample.datasource.service.UserService;
import org.hongxi.whatsmars.common.result.Result;
import org.hongxi.whatsmars.common.result.ResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shenhongxi on 2017/11/16.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @GetMapping("/find/{username}")
    public Result<User> find(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        return ResultHelper.newSuccessResult(user);
    }

    @PostMapping("/add")
    public Result add(@RequestParam(name = "name") String username,
                          @RequestParam(required = false) String nickname,
                          @RequestParam(required = false, defaultValue = "1") Integer gender,
                          @RequestParam Integer age) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setGender(gender);
        user.setAge(age);
        userService.add(user);
        return ResultHelper.newSuccessResult();
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user) { // 以json格式接收参数, RequestBody也可省略
        userService.update(user);
        return ResultHelper.newSuccessResult();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Long id) {
        userService.delete(id);
        return ResultHelper.newSuccessResult();
    }

    @GetMapping("/query")
    public ReturnItems<User> query(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                   @RequestParam(value = "limit", defaultValue = "30") Integer limit) {
        Page<User> page = userService.query(offset, limit);
        return ReturnItemUtils.newSuccessReturnItems(page.getResult(), page.getTotal());
    }

    @GetMapping("/findByNicknameAndGender")
    public ReturnItems<User> findByNicknameAndGender(@RequestParam String nickname,
                                                     @RequestParam Integer gender) {
        ReturnItems<User> returnItems = new ReturnItems<>();
        List<User> users = userService.findByNicknameAndGender(nickname, gender);
        long total = users == null ? 0 : users.size();
        return ReturnItemUtils.newSuccessReturnItems(users, total);
    }

    @PostMapping("/addBatch")
    public Result addBatch() {
        try {
            userService.testTransaction(buildUsers());
        } catch (Exception e) {
            logger.error("#########user addBatch error", e);
            return ResultHelper.newErrorResult();
        }

        return ResultHelper.newSuccessResult();
    }

    private List<User> buildUsers() {
        List<User> users = new ArrayList<>();
        Date now = new Date();
        long t = now.getTime();
        User user = new User();
        user.setUsername("tb" + t++);
        user.setNickname("hongxi");
        user.setGender(1);
        user.setAge(28);
        user.setCreateDate(now);
        user.setUpdateDate(now);
        users.add(user);
        user = new User();
        user.setUsername("tb" + t++);
        user.setNickname("lilei");
        user.setGender(1);
        user.setAge(27);
        user.setCreateDate(now);
        user.setUpdateDate(now);
        users.add(user);
        return users;
    }
}
