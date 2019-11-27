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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Arrays;

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

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SpringSessionBackedSessionRegistry sessionRegistry =new SpringSessionBackedSessionRegistry(sessionRepository);
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
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
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
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry);
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

    /**
     * 在声明一个passwordEncoder的bean之后，springSecurity会自动应用
     *
     * 下面的算法盐随机产生，不再保存数据库
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        //允许跨域访问本服务的站点,add为添加一个,set为添加多个
        corsConfiguration.addAllowedOrigin("*");
        //下面的配置仅在OPTIONS预检请求的响应值指定有效，用于表明浏览器允许跨域的HTTP方法
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST"));
        //当下面字段设置为true时,浏览器会在接下来的真实请求中携带用户的cookie信息，服务器也可以使用set-coolie向用户写入新cookie
        //使用true时候,allowRrigin不可设置为*
        corsConfiguration.setAllowCredentials(false);

        //对所有URL生效
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return configurationSource;
    }

}
