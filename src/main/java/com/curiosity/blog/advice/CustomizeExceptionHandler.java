package com.curiosity.blog.advice;

import com.alibaba.fastjson.JSON;
import com.curiosity.blog.dto.ResultDto;
import com.curiosity.blog.exception.CustomizeErrorCodeImpl;
import com.curiosity.blog.exception.CustomsizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(CustomsizeException.class)
    ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Throwable ex, Model model) {
        HttpStatus status = getStatus(request);
        ResultDto resultDto = null;
        if ("application/json".equals(request.getContentType())) {
            if (ex instanceof CustomsizeException) {
                resultDto = ResultDto.errorof((CustomsizeException) ex);
            } else {
                resultDto = ResultDto.errorof(CustomizeErrorCodeImpl.SYS_ERROR);

            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        if (ex instanceof CustomsizeException) {
            model.addAttribute("message", ((CustomsizeException) ex).getCustomizeErrorCode().getMsg());
        } else {
            model.addAttribute("message", "服务器炸了，待会过来吧");

        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
