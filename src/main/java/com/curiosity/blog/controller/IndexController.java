package com.curiosity.blog.controller;

import com.curiosity.blog.dto.PaginationDto;
import com.curiosity.blog.mapper.UserMapper;
import com.curiosity.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/16
 */

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size
    ) {


        PaginationDto paginationDto = questionService.list(page,size);
        model.addAttribute("pagination", paginationDto);
        return "index";
    }

}
