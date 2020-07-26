package com.curiosity.blog.controller;

import com.curiosity.blog.dto.PaginationDto;
import com.curiosity.blog.mapper.NotificationMapper;
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
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationServie notificationServie;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        Long unreadCount = notificationServie.unreadCount(user.getId());
        model.addAttribute("unreadCount", unreadCount);
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDto paginationDto = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDto);
        } else if ("repies".equals(action)) {
            PaginationDto paginationDto = notificationServie.list(user.getId(), page, size);
            model.addAttribute("section", "repies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pagination", paginationDto);
        }
        return "profile";
    }

}
