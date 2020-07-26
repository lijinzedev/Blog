package com.curiosity.blog.module;

import lombok.Data;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/25
 */
@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerid;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}
