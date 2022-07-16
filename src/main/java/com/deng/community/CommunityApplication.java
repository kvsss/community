package com.deng.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class CommunityApplication {


    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
