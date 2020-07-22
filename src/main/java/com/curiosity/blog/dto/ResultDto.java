package com.curiosity.blog.dto;

import com.curiosity.blog.exception.CustomizeErrorCodeImpl;
import com.curiosity.blog.exception.CustomsizeException;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/22
 */
@Data
public class ResultDto {
    private Integer code;
    private String messge;

    public static ResultDto errorof(Integer code, String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessge(message);
        return resultDto;
    }

    public static ResultDto errorof(CustomizeErrorCodeImpl userNotFount) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(userNotFount.getCode());
        resultDto.setMessge(userNotFount.getMsg());
        return resultDto;
    }
    public static ResultDto ok() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessge("请求成功");
        return resultDto;
    }

    public static ResultDto errorof(CustomsizeException ex) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ex.getCustomizeErrorCode().getCode());
        resultDto.setMessge(ex.getCustomizeErrorCode().getMsg());
        return resultDto;
    }
}
