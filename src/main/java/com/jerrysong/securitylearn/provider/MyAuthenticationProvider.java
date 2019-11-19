package com.jerrysong.securitylearn.provider;

import com.jerrysong.securitylearn.exception.VerifyCodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    public MyAuthenticationProvider(UserDetailsService userDetailService,PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(userDetailService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails,
            UsernamePasswordAuthenticationToken authenticationToken)
            throws AuthenticationException {
        //实现图形验证码逻辑
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authenticationToken.getDetails();
        if(!details.getVerifyCodeCorrect())
            throw new VerifyCodeException();


        //调用父类的方法实现用户名密码校验
        super.additionalAuthenticationChecks(userDetails, authenticationToken);
    }
}
