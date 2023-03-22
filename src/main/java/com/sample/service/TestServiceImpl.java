package com.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2022/3/24 下午3:32
 */
@Slf4j
@Service
public class TestServiceImpl {

    public void test1() {
        log.info("调用test1方法");
    }
}
