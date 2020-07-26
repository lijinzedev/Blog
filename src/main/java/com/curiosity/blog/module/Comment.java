package com.curiosity.blog.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    public Long id;
    private Long parentId;
    private Integer type;
    private Long commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private Integer commentCount;
    private String content;
}
