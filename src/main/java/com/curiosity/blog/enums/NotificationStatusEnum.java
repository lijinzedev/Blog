package com.curiosity.blog.enums;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/25
 */

public enum  NotificationStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }

}
