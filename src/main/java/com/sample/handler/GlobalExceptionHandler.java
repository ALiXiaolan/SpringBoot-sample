package com.sample.handler;

import cn.dev33.satoken.util.SaResult;
import com.sample.core.Constants;
import com.sample.data.ApiData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * sa-Token全局拦截器
 *
 * @author xiaolan, created on 2023/3/21
 * @version 0.1.0-SNAPSHOT
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        log.error("sa-token error:{}", e.getMessage());
        return SaResult.error(e.getMessage());
    }
}
