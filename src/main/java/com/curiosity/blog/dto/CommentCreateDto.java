package com.curiosity.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {
//    {
//        "parentId":16,
//            "content":"恢复内容",
//            "type":1
//    }

    private Long parentId;
    private String content;
    private Integer type;

}
