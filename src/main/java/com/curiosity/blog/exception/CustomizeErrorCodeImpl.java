package com.curiosity.blog.exception;

public enum CustomizeErrorCodeImpl implements CustomizeErrorCode {
    QUESTION_NOT_FOUNT("您搜索的问题不存在"),
    NETWORK_NO_CONNECTION("网络异常，请检查网络");
    private String msg;
    CustomizeErrorCodeImpl(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;

    }
}
