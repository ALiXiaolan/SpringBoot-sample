package com.sample.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xiaolan, created on 2023/3/21
 * @version 0.1.0-SNAPSHOT
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册 Sa-Token拦截器，打开注解式鉴权功能嫩,默认是关闭状态

        registry.addInterceptor(new SaInterceptor(handle -> {
            //登陆校验
            SaRouter.match("/**", "/doLogin", r -> StpUtil.checkLogin());
            //角色校验
            //SaRouter.match("/admin/**", r -> StpUtil.checkRoleOr("admin", "super-admin"));
        }));
    }
}
