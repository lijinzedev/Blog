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
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
