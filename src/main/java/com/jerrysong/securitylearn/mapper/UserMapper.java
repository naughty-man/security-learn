package com.jerrysong.securitylearn.mapper;

import com.jerrysong.securitylearn.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from users where username=#{username}")
    User findByUserName(@Param("username") String username);

}
