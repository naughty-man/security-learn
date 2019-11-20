package com.jerrysong.securitylearn.provider;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean verifyCodeCorrect;

    public boolean getVerifyCodeCorrect(){
        return this.verifyCodeCorrect;
    }

    //补充用户提交的验证码和session保存的验证码
    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String requestCode=request.getParameter("kaptcha");
        HttpSession session=request.getSession();
        String savedCode=(String) session.getAttribute("kaptcha");
        //随手清除验证码，无论是失败还是成功，客户端应在登录失败时刷新验证码
        session.removeAttribute("kaptcha");
        if(savedCode!=null&& !"".equals(savedCode)){
            if(Objects.equals(requestCode,savedCode)){
                this.verifyCodeCorrect=true;
            }
        }
    }
}
