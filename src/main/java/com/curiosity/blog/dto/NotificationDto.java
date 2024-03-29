package com.curiosity.blog.dto;

import com.curiosity.blog.module.User;
import lombok.Data;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/25
 */
@Data
public class NotificationDto {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
