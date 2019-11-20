package com.jerrysong.securitylearn.config;

import com.jerrysong.securitylearn.handler.MyAuthenticationFailureHandler;
import com.jerrysong.securitylearn.provider.MyAuthenticationProvider;
import com.jerrysong.securitylearn.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

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
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        AuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider(userDetailsService,passwordEncoder);
        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Autowired
    private JdbcTokenRepositoryImpl jdbcTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers(
                        "/app/api/**",
                        //开放图形验证码的访问权限
                        "/kaptcha.jpg",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.jpg"
                        )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                    .authenticationDetailsSource(myWebAuthenticationDetailsSource)
                    .loginPage("/myLogin.html")
                    .loginProcessingUrl("/executeLogin")
                    .successForwardUrl("/success")
                    .failureHandler(myAuthenticationFailureHandler)
                .permitAll()
                .and()
                    .rememberMe()
                        .userDetailsService(userDetailsService)
                        .tokenRepository(jdbcTokenRepository)
                .and()
                    .sessionManagement()
                .maximumSessions(1);
        http
                .logout()
                    .logoutUrl("/myLogout")
                //注销成功，重定向路径
                    .logoutSuccessUrl("/myLogin.html")
                //不同于logoutSuccessUrl的重定向，logoutSuccessHandler更加灵活
//                    .logoutSuccessHandler(new LogoutSuccessHandler() {
//                        @Override
//                        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//                        }
//                    })
                //使该用户的httpSession失效
                .invalidateHttpSession(true)
                //注销成功，删除指定cookie
                .deleteCookies("cookie1","cookie2");
    }
}
