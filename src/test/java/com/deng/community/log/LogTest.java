package com.deng.community.log;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author :deng
 * @version :1.0
 * @since :1.8
 */
@SpringBootTest
public class LogTest {

    private static final Logger log = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void fun() {
        log.debug("tips");
        log.info("tips");
        log.warn("tips");
        log.error("tips");

    }

}
