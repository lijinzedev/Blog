package com.curiosity.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/25
 */

@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
