package com.jerrysong.securitylearn.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Autowired
    private Producer kaptchaProducer;

    @GetMapping("/kaptcha.jpg")
    public void getKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        String kapText = kaptchaProducer.createText();
        //将验证码文本设置到session
        request.getSession().setAttribute("kaptcha",kapText);
        //创建验证码图片
        BufferedImage bi =kaptchaProducer.createImage(kapText);
        //获取响应输出流
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi,"jpg",out);
        try{
            //推送响应输出流
            out.flush();
        }finally {
            out.close();
        }
    }
}
