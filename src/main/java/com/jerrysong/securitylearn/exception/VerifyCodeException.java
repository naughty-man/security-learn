package com.jerrysong.securitylearn.exception;

import org.springframework.security.core.AuthenticationException;

public class VerifyCodeException extends AuthenticationException {
    public VerifyCodeException() {
        super("图形验证码校验失败");
    }
}
