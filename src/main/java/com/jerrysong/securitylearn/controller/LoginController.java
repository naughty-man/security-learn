package com.jerrysong.securitylearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/myLogin.html")
    public String login(){
        return "pages/page1/hi";
    }

    @PostMapping("/success")
    public String success(){
        return "pages/page1/success";
    }

}
