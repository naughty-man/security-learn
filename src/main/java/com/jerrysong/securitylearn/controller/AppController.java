package com.jerrysong.securitylearn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下面内容是面向客户端公开访问API
 *
 * @Author Jerry.Song
 * @Date 2019/11/19 10:42
 */
@RestController
@RequestMapping("/app/api")
public class AppController {
    @GetMapping("/hello")
    public String hello(){
        return "hello,app";
    }
}
