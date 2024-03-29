package com.curiosity.blog.controller;

import com.curiosity.blog.cache.TagCache;
import com.curiosity.blog.dto.CommentDto;
import com.curiosity.blog.dto.QuestionDto;
import com.curiosity.blog.enums.CommentTypeEnum;
import com.curiosity.blog.service.CommentService;
import com.curiosity.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model) {

        QuestionDto dto = questionService.getById(id);
        // 每次访问的时候累加浏览
        questionService.incView(id);
        List<CommentDto> commentDtos = commentService.listByQuestionIdAndType(id, CommentTypeEnum.QUESTION);
        List<QuestionDto> relatedQuestion = questionService.selectRelated(dto);
        model.addAttribute("question", dto);
        model.addAttribute("coments", commentDtos);
        model.addAttribute("relatedQuestion", relatedQuestion);
        model.addAttribute("tags", TagCache.get());
        return "question";
    }


}
