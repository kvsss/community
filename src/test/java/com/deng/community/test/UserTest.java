package com.deng.community.test;

import com.deng.community.mapper.DiscussPostMapper;
import com.deng.community.mapper.UserMapper;
import com.deng.community.entity.DiscussPost;
import com.deng.community.entity.User;
import com.deng.community.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @since :1.8
 */
@SpringBootTest
public class UserTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DiscussPostMapper discussPostDao;

    @Autowired
    UserService userService;

    @Test
    public void t1() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    public void t2() {
        List<DiscussPost> discussPosts = discussPostDao.selectDiscussPosts(0, 0, 10);
        for (DiscussPost discussPost : discussPosts) {
            System.out.println(discussPost);
        }

        System.out.println(discussPostDao.selectDiscussPostRows(0));
    }

    @Test
    public void t3() {
        System.out.println(userService.findUserById(0));
    }


}
