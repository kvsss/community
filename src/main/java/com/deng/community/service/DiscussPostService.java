package com.deng.community.service;


import com.deng.community.dao.DiscussPostDao;
import com.deng.community.pojo.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostDao discussPostDao;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostDao.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostDao.selectDiscussPostRows(userId);
    }

}
