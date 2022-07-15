package com.deng.community.dao;

import com.deng.community.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    User selectById(@Param("id") int id);
    User selectByUsername(@Param("username") String username);
    User selectByEmail(@Param("email") String email);
    int insertUser(User user);
    int updateStatus(@Param("id") int id, @Param("status") Integer status);
    int updateHeaderUrl(@Param("id") int id, @Param("headerUrl") String headerUrl);
    int updatePassword(@Param("id") int id, @Param("password") String password);
}
