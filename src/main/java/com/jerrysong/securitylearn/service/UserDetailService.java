package com.jerrysong.securitylearn.service;

import com.jerrysong.securitylearn.domain.User;
import com.jerrysong.securitylearn.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(username);
        if(user==null)
            throw new UsernameNotFoundException("用户不存在");
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
        return user;
    }

    //自行实现的权限转化
    private List<GrantedAuthority> generateAuthorities(String roles){
        List<GrantedAuthority> authorites=new ArrayList<>();
        if(roles!=null&&"".equals(roles)){
            String[] roleArray=roles.split(";");
            for(String role:roleArray){
                authorites.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorites;

    }
}