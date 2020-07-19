package com.curiosity.blog.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private String avatarUrl;
    private Long gmtCreate;
    private Long gmtModify;
}
