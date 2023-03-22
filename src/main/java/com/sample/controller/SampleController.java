package com.sample.controller;

import com.sample.data.ApiData;
import com.sample.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolan
 * @version 1.0.0-SNAPSHOT
 * @date 2021/11/22 上午11:04
 */
@Slf4j
//@Api(tags = "招呼页")
@RequestMapping(value = "/sample")
@RestController
public class SampleController {

    private int i = 0;

    @GetMapping(value = "/test")
    public ApiData<Integer> test() {
        i++;
        return new ApiData<>(i);
    }

    //@LogRecordAnnotation(success = "调用成功",prefix = "test",bizNo = "1111111")
    @GetMapping(value = "/test1")
    public ApiData<User> test1() {
        return new ApiData<>(new User("哪吒"));
    }


    //@ApiOperation(value = "问好")
    @GetMapping(value = "/hello")
    public ApiData<String> hello() {
        log.info("String isBank:{}", StringUtils.isBlank(" "));
        log.info("String isEmpty:{}", StringUtils.isNotBlank(" "));
        return new ApiData<>("hi,早啊");
    }
}
