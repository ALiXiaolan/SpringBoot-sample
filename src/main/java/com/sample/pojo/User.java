package com.sample.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaolan
 * @version 1.0.0-SNAPSHOT
 * @date 2021/12/16 下午3:57
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    //@ApiModelProperty(value = "名称")
    String name;
}
