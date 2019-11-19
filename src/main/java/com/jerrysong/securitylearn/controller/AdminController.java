package com.jerrysong.securitylearn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下面的内容是后台管理相关API，必须拥有管理员权限才能继续操作
 *
 * @Author Jerry.Song
 * @Date 2019/11/19 10:42
 */
@RestController
@RequestMapping("/admin/api")
public class AdminController {
    @GetMapping("/hello")
    public String hello(){
        return "hello,admin";
    }
}
