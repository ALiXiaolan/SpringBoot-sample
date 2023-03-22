package com.sample;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaolan
 * @version 1.0.0-SNAPSHOT
 * @date 2021/11/22 下午4:26
 */
@Slf4j
public class ThreeLocal {

    /**
     * 机器cpu核数
     */
    private int processors = Runtime.getRuntime().availableProcessors();

    public void thread() {
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(
                        processors * 3,
                        processors * 5,
                        30, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>());
    }

    @Test
    public void test() {
        log.info("{}", StringUtils.isBlank(" "));
        log.info("{}", StringUtils.isEmpty(" "));
    }

}
