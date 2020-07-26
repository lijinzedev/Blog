package com.curiosity.blog.controller;

import com.curiosity.blog.dto.NotificationDto;
import com.curiosity.blog.dto.PaginationDto;
import com.curiosity.blog.module.User;
import com.curiosity.blog.service.NotificationServie;
import com.curiosity.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/25
 */

@Controller
public class NotificationController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationServie notificationServie;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDto notification = notificationServie.read(user.getId(), id);

        return "redirect:/question/" + notification.getOuterid();
    }
}
