package com.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2022/3/24 下午3:23
 */
@Slf4j
@Service
public class SampleServiceImpl {

    public void test2() {
        log.info("调用test2方法");
    }

}
