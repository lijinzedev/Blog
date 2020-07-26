package com.curiosity.blog.controller;

import com.curiosity.blog.dto.CommentCreateDto;
import com.curiosity.blog.dto.CommentDto;
import com.curiosity.blog.dto.ResultDto;
import com.curiosity.blog.enums.CommentTypeEnum;
import com.curiosity.blog.exception.CustomizeErrorCodeImpl;
import com.curiosity.blog.mapper.NotificationMapper;
import com.curiosity.blog.module.Comment;
import com.curiosity.blog.module.User;
import com.curiosity.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public Object post(@RequestBody CommentCreateDto commentDto, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorof(CustomizeErrorCodeImpl.USER_NOT_FOUNT);
        } else if (commentDto == null || StringUtils.isEmpty(commentDto.getContent())) {
            return ResultDto.errorof(CustomizeErrorCodeImpl.Comment_NOT_NULL);
        } else {
            Comment comment = new Comment();
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            comment.setContent(commentDto.getContent());
            comment.setParentId(commentDto.getParentId());
            comment.setType(commentDto.getType());
            comment.setCommentor(user.getId());
            commentService.insert(comment,user);
        }

        return ResultDto.ok(null);
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDto comments(@PathVariable("id") Long id) {
        List<CommentDto> commentDtos = commentService.listByQuestionIdAndType(id, CommentTypeEnum.COMMENT);

        return ResultDto.ok(commentDtos);
    }
}
