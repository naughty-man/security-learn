package com.jerrysong.securitylearn.filter;//package com.jerrysong.security.filter;
//
//import com.jerrysong.security.exception.VerifyCodeException;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.Objects;
//
///**
// * 下面是采用filter方式校验图形验证码，属于serlvet层面，简单，易于理解
// * security还提供了一种更优雅的实现图形验证码的方式，即自定义认证
// */
////@Component
//public class VerifyCodeFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        //非登录请求不校验验证码
//        if(!"/executeLogin".equals(httpServletRequest.getRequestURI())){
//            filterChain.doFilter(httpServletRequest,httpServletResponse);
//        }else{
//            try{
//                verifyCode(httpServletRequest);
//                filterChain.doFilter(httpServletRequest,httpServletResponse);
//            }catch (VerifyCodeException e){
//                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
//            }
//        }
//    }
//
//    private void verifyCode(HttpServletRequest httpServletRequest) {
//        String requestCode=httpServletRequest.getParameter("kaptcha");
//        HttpSession session=httpServletRequest.getSession();
//        String savedCode=(String) session.getAttribute("kaptcha");
//        if(savedCode!=null&&savedCode!=""){
//            //随手清除验证码，无论是失败还是成功，客户端应在登录失败时刷新验证码
//            session.removeAttribute("kaptcha");
//        }
//
//        //校验不通过，抛出异常
//        if (StringUtils.isBlank(requestCode)
//                || StringUtils.isBlank(savedCode)
//                || !Objects.equals(requestCode, savedCode)) {
//            throw new VerifyCodeException();
//        }
//    }
//}
