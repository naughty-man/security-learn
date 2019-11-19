package com.jerrysong.securitylearn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下面内容是用户操作自身数据相关API，必须用户登录后才能进行操作
 *
 * @Author Jerry.Song
 * @Date 2019/11/19 10:42
 */
@RestController
@RequestMapping("/user/api")
public class UserController {
    @GetMapping("/hello")
    public String hello(){
        return "hello,user";
    }
}
