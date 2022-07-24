package com.deng.community.test;

import com.deng.community.entity.LoginTicket;
import com.deng.community.mapper.DiscussPostMapper;
import com.deng.community.mapper.UserMapper;
import com.deng.community.entity.DiscussPost;
import com.deng.community.entity.User;
import com.deng.community.service.LikeService;
import com.deng.community.service.UserService;
import com.deng.community.util.CommunityConstant;
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
public class UserTest implements CommunityConstant {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DiscussPostMapper discussPostDao;

    @Autowired
    UserService userService;


    @Autowired
    LikeService likeService;

    @Test
    public void t1() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    public void t2() {
        List<DiscussPost> discussPosts = discussPostDao.selectDiscussPosts(0, 0, 10,0);
        for (DiscussPost discussPost : discussPosts) {
            System.out.println(discussPost);
        }

        System.out.println(discussPostDao.selectDiscussPostRows(0));
    }

    @Test
    public void t3() {
        System.out.println(userService.findUserById(0));
    }


    @Test
    public void t4() {
        LoginTicket ticket = userService.findLoginTicket("b411b42410bf4abe8d9166fc8a8f993a");
        System.out.println(ticket);
    }


    @Test
    public void t5() {
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, 288);
        System.out.println(likeCount);
    }

}
