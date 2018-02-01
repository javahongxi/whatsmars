package org.hongxi.whatsmars.spring.boot.controller;

import com.github.pagehelper.Page;
import org.hongxi.whatsmars.spring.boot.common.pojo.ReturnItems;
import org.hongxi.whatsmars.spring.boot.exception.AppException;
import org.hongxi.whatsmars.spring.boot.model.User;
import org.hongxi.whatsmars.spring.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2017/11/16.
 */
@RestController
@RequestMapping("/new")
public class NewController {

    @Autowired
    private UserService userService;

    @GetMapping("/t/{userId}")
    public Map<String, Object> query(@PathVariable("userId") Integer userId) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("domain", "toutiao.im");
        return m;
    }

    @PostMapping("/t")
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

    @PutMapping("/t")
    public HttpStatus update(@RequestBody User user) { // 以json格式接收参数, RequestBody也可省略
        userService.update(user);
        return HttpStatus.OK;
    }

    @DeleteMapping("/t")
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
}
