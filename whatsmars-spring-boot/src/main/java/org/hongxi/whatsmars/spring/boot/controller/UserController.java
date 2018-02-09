package org.hongxi.whatsmars.spring.boot.controller;

import com.github.pagehelper.Page;
import org.hongxi.whatsmars.spring.boot.common.pojo.ReturnItems;
import org.hongxi.whatsmars.spring.boot.exception.AppException;
import org.hongxi.whatsmars.spring.boot.model.User;
import org.hongxi.whatsmars.spring.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by shenhongxi on 2017/11/16.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/find/{username}")
    public User find(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/add")
    public HttpStatus add(@RequestParam(name = "name") String username,
                          @RequestParam(required = false) String nickname,
                          @RequestParam(required = false, defaultValue = "1") Integer gender,
                          @RequestParam Integer age) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setGender(gender);
        user.setAge(age);
        userService.add(user);
        return HttpStatus.OK;
    }

    @PutMapping("/update")
    public HttpStatus update(@RequestBody User user) { // 以json格式接收参数, RequestBody也可省略
        userService.update(user);
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete")
    public HttpStatus delete(@RequestParam Long id) {
        userService.delete(id);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/e", method = RequestMethod.GET)
    public HttpStatus e(@RequestParam(required = false) String error) {
        if (error != null) throw AppException.build(error);
        return HttpStatus.OK;
    }

    @GetMapping("/query")
    public ReturnItems<User> query(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                   @RequestParam(value = "limit", defaultValue = "30") Integer limit) {
        ReturnItems<User> returnItems = new ReturnItems<>();
        Page<User> page = userService.query(offset, limit);
        returnItems.setItems(page.getResult());
        returnItems.setTotal(page.getTotal());
        returnItems.setStatus(200);
        return returnItems;
    }

    @GetMapping("/findByNicknameAndGender")
    public ReturnItems<User> findByNicknameAndGender(@RequestParam String nickname,
                                                     @RequestParam Integer gender) {
        ReturnItems<User> returnItems = new ReturnItems<>();
        List<User> users = userService.findByNicknameAndGender(nickname, gender);
        returnItems.setItems(users);
        returnItems.setTotal(users == null ? 0 : users.size());
        returnItems.setStatus(200);
        return returnItems;
    }

    @PostMapping("/addBatch")
    public HttpStatus addBatch() {
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
        userService.addBatch(users);
        return HttpStatus.OK;
    }
}
