package com.deng.community.service;

import com.deng.community.dao.UserDao;

import com.deng.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findUserById(int id) {
        return userDao.selectById(id);
    }

}
