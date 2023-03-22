package com.sample;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2022/3/24 下午5:07
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //System.out.println("启动成功:Sa-Token配置如下:" + SaManager.getConfig());
    }
}
