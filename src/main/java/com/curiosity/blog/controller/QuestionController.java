package com.curiosity.blog.controller;

import com.curiosity.blog.dto.QuestionDto;
import com.curiosity.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService service;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        QuestionDto dto = service.getById(id);
        model.addAttribute("question", dto);
        return "question";
    }
}
