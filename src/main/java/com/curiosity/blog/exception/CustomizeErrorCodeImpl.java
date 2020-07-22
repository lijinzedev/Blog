package com.curiosity.blog.exception;

public enum CustomizeErrorCodeImpl implements CustomizeErrorCode {
    NETWORK_NO_CONNECTION(5001, "网络异常，请检查网络"),
    QUESTION_NOT_FOUNT(2001, "您搜索的问题不存在"),
    TARGET_PARAM_NOT_FOUNT(2002, "未选中任何问题或者评论进行恢复"),
    USER_NOT_FOUNT(2003, "未登录不能进行评论，请先登录"),
    SYS_ERROR(2004, "服务器炸了"),
    TYPE_PARAM_WRONG(2005, "类型不匹配"),
    COMMENT_NOT_FOUNT(2006, "回复的评论不存在了要不换个试试？");
    private String msg;
    private Integer code;

    CustomizeErrorCodeImpl(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;

    }
}
