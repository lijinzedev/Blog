package com.curiosity.blog.advice;

import com.curiosity.blog.exception.CustomsizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(CustomsizeException.class)
    ModelAndView handler(HttpServletRequest request, Throwable ex, Model model) {
        HttpStatus status = getStatus(request);
        if (ex instanceof CustomsizeException){
            model.addAttribute("message",((CustomsizeException) ex).getCustomizeErrorCode().getMsg());
        }else {
            model.addAttribute("message","服务器炸了，待会过来吧");

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
