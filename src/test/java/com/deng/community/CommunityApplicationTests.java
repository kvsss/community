package com.deng.community;

import com.deng.community.temp.TempService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    ApplicationContext context;


    @Test
    void contextLoads() {
        TempService tempService = context.getBean(TempService.class);
        tempService.fun();
    }


}
