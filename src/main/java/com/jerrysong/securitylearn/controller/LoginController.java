package com.jerrysong.securitylearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/myLogin.html")
    public String login(){
        return "pages/page1/hi";
    }

}
