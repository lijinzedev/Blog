package com.curiosity.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GithubUserDto {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

}
