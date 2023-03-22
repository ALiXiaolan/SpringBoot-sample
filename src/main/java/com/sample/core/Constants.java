package com.sample.core;

/**
 * @author xiaolan
 * @version 1.0.0-SNAPSHOT
 * @date 2021/11/26 下午3:19
 */
public interface Constants {

    interface ApiCode {
        /**
         * 返回正常
         */
        int OK = 200;

        /**
         * 登陆失败
         */
        int LOGIN_FAIL = 401;

        /**
         * 返回失败
         */
        int FAIL = 500;
    }
}
