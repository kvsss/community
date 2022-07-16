package com.deng.community.test;

import com.deng.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author :deng
 * @version :1.0
 * @since :1.8
 */
@SpringBootTest
public class MailTest {
    @Autowired
    MailClient mailClient;


    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void t1() {
        mailClient.sendMail("912068332@qq.com", "你好", "welcome");
    }

}
