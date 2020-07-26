package com.curiosity.blog.controller;

import com.curiosity.blog.cache.TagCache;
import com.curiosity.blog.dto.QuestionDto;
import com.curiosity.blog.module.Question;
import com.curiosity.blog.module.User;

import com.curiosity.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/18
 */
@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        QuestionDto question = questionService.getById(id);
        if (question != null) {
            model.addAttribute("title", question.getTitle());
            model.addAttribute("description", question.getDescription());
            model.addAttribute("tag", question.getTag());
            model.addAttribute("id", question.getId());
            return "publish";

        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/publish")
    public String publish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "tag", required = false) String tag,

            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);



        if (StringUtils.isEmpty(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isEmpty(description)) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (StringUtils.isEmpty(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isEmpty(invalid)) {
            model.addAttribute("error", "输入非法标签:" );
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        } else {
            Question question = new Question();
            question.setCreator(user.getId());
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setId(id);
            questionService.createOrUpdate(question);

        }
        return "redirect:/";
    }


}
