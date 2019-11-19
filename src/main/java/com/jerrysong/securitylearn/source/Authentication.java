package com.jerrysong.securitylearn.source;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * AuthenticationProvider被Spring Security定义为一个验证过程。
 */
public interface Authentication extends Principal, Serializable {
    // ~ Methods
    // ========================================================================================================
    //获取主体权限列表
    Collection<? extends GrantedAuthority> getAuthorities();
    //获取主体凭证，通常为用户密码
    Object getCredentials();
    //获取主体携带的详细信息
    Object getDetails();
    //获取主体，通常为一个用户名
    Object getPrincipal();
    //主体是否验证成功
    boolean isAuthenticated();
    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}

