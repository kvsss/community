package com.deng.community.temp;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author :deng
 * @version :1.0
 * @since :1.8
 */
@Service
public class TempService {
    public TempService() {
        System.out.println("构造");
    }

    @PostConstruct
    public void init() {
        System.out.println("init");
    }


    public void fun() {
        System.out.println("fun");
    }


    @PreDestroy
    public void destroy() {
        System.out.println("销毁前");
    }


    public void fun1() throws Exception {
    }

}
