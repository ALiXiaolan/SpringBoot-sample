package com.sample.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.sample.data.ApiData;
import com.sample.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolan, created on 2023/3/20
 * @version 0.1.0-SNAPSHOT
 */
@RestController("/user")
public class UserController {

    @GetMapping("/doLogin")
    public ApiData<SaResult> doLogin(@RequestParam(value = "username") String username,
                                     @RequestParam(value = "pwd") String pwd) {
        //模拟用户
        if ("zhang".equals(username) && "123456".equals(pwd)) {
            StpUtil.login(10001);
            return new ApiData<>(SaResult.ok());
        }
        return new ApiData<>(SaResult.error());
    }

    @GetMapping("/isLogin")
    public ApiData<String> isLogin() {
        return new ApiData<>("当前会话是否登陆:" + StpUtil.isLogin());
    }

    @GetMapping("/getTokenInfo")
    public ApiData<SaResult> getTokenInfo() {
        return new ApiData<>(SaResult.data(StpUtil.getTokenInfo()));
    }

    @GetMapping("/getUserInfo")
    public ApiData<User> getUserInfo() {
        return new ApiData<>(new User("zhang"));
    }
}
