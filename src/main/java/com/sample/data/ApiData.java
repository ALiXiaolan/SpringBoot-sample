package com.sample.data;

import com.sample.core.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应数据
 *
 * @param <T>支持范型
 * @author xiaolan
 * @version 1.0.0-SNAPSHOT
 * @date 2021/11/22 下午3:00
 */
@Data
@NoArgsConstructor
//@ApiModel(description = "返回数据对象")
public class ApiData<T> {

    //@ApiModelProperty(position = 1, value = "状态码：200请求成功")
    private Integer code = Constants.ApiCode.OK;
    //@ApiModelProperty(position = 2, value = "错误信息")
    private String msg;
    //@ApiModelProperty(value = "返回数据")
    private T data;

    public ApiData(T data) {
        this.data = data;
    }

    public ApiData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
