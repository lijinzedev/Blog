package com.curiosity.blog.service;

import com.curiosity.blog.enums.CommentTypeEnum;
import com.curiosity.blog.exception.CustomizeErrorCodeImpl;
import com.curiosity.blog.exception.CustomsizeException;
import com.curiosity.blog.mapper.CommentMapper;
import com.curiosity.blog.mapper.QuestionMapper;
import com.curiosity.blog.module.Comment;
import com.curiosity.blog.module.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/22
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomsizeException(CustomizeErrorCodeImpl.TARGET_PARAM_NOT_FOUNT);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomsizeException(CustomizeErrorCodeImpl.TYPE_PARAM_WRONG);
        }
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //回复评论
            Comment dbComment = commentMapper.selectByParentId(comment.getParentId());
            if (dbComment == null) {
                throw new CustomsizeException(CustomizeErrorCodeImpl.COMMENT_NOT_FOUNT);
            }
            commentMapper.insert(comment);
        } else if (comment.getType().equals(CommentTypeEnum.QUESTION.getType())) {
            // 回复问题
            Question dbCuestion = questionMapper.getById(comment.getParentId());
            if (dbCuestion == null) {
                throw new CustomsizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUNT);
            }
            commentMapper.insert(comment);
            questionMapper.incCommenCount(dbCuestion);
        }

    }
}
