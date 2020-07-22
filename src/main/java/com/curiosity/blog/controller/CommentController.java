package com.curiosity.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.curiosity.blog.dto.CommentDto;
import com.curiosity.blog.dto.ResultDto;
import com.curiosity.blog.exception.CustomizeErrorCodeImpl;
import com.curiosity.blog.mapper.CommentMapper;
import com.curiosity.blog.module.Comment;
import com.curiosity.blog.module.User;
import com.curiosity.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/21
 */

@Controller
@ResponseBody

public class CommentController {


    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public Object post(@RequestBody CommentDto commentDto, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorof(CustomizeErrorCodeImpl.USER_NOT_FOUNT);
        } else {
            Comment comment = new Comment();
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            comment.setContent(commentDto.getContent());
            comment.setParentId(commentDto.getParentId());
            comment.setType(commentDto.getType());
            comment.setCommentor(user.getId());

            commentService.insert(comment);
        }

        return ResultDto.ok();
    }
}
