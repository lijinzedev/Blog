package com.curiosity.blog.dto;

import com.curiosity.blog.module.Question;
import com.curiosity.blog.module.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    // 评论数
    private int commentCount;
    // 浏览数
    private int viewCount;
    // 点赞数
    private int likeCount;
    private String tag;
    private User user;

    public static QuestionDto convert(Question question) {
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        return questionDto;
    }
}
