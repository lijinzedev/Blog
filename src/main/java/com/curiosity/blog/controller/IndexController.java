package com.curiosity.blog.controller;

import com.curiosity.blog.dto.PaginationDto;
import com.curiosity.blog.dto.QuestionDto;
import com.curiosity.blog.mapper.QuestionMapper;
import com.curiosity.blog.mapper.UserMapper;
import com.curiosity.blog.module.Question;
import com.curiosity.blog.module.User;
import com.curiosity.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

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
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String token = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("token"))
                    .map(cookie -> cookie.getValue()).findFirst().get();
            User user = null;
            if (!StringUtils.isEmpty(token)) {
                user = userMapper.findByToken(token);
            }
            if (user != null) {
                request.getSession().setAttribute("user", user);

            }
        }

        PaginationDto paginationDto = questionService.list(page,size);
        model.addAttribute("pagination", paginationDto);
        return "index";
    }

}
