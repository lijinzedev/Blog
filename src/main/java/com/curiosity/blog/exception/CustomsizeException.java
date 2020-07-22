package com.curiosity.blog.exception;


public class CustomsizeException extends RuntimeException {
    private CustomizeErrorCode customizeErrorCode;

    public CustomizeErrorCode getCustomizeErrorCode() {
        return customizeErrorCode;
    }


    public CustomsizeException(CustomizeErrorCode customizeErrorCode) {

        this.customizeErrorCode = customizeErrorCode;
    }
}
