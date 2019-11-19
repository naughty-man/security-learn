package com.jerrysong.securitylearn.config;

import com.jerrysong.securitylearn.handler.MyAuthenticationFailureHandler;
import com.jerrysong.securitylearn.provider.MyAuthenticationProvider;
import com.jerrysong.securitylearn.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest,
            WebAuthenticationDetails> myWebAuthenticationDetailsSource;
    @Autowired
    UserDetailService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        AuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider(userDetailsService,passwordEncoder);
        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers(
                        "/app/api/**",
                        //开放图形验证码的访问权限
                        "/kaptcha.jpg")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                    .authenticationDetailsSource(myWebAuthenticationDetailsSource)
                    .loginPage("/myLogin.html")
                    .loginProcessingUrl("/executeLogin")
                    .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error_code\":\"0\",\"message\":\"欢迎\"}");
                    })
                    .failureHandler(myAuthenticationFailureHandler)
                .permitAll();
    }
}
