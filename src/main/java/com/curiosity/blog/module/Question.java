package com.curiosity.blog.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    // 评论数
    private int commentCount;
    // 浏览数
    private int viewCount;
    // 点赞数
    private int likeCount;
    private String tag;
}
