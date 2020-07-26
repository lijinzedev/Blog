package com.curiosity.blog.service;

import com.curiosity.blog.dto.CommentDto;
import com.curiosity.blog.enums.CommentTypeEnum;
import com.curiosity.blog.enums.NotificationStatusEnum;
import com.curiosity.blog.enums.NotificationTypeEnum;
import com.curiosity.blog.exception.CustomizeErrorCodeImpl;
import com.curiosity.blog.exception.CustomsizeException;
import com.curiosity.blog.mapper.CommentMapper;
import com.curiosity.blog.mapper.NotificationMapper;
import com.curiosity.blog.mapper.QuestionMapper;
import com.curiosity.blog.mapper.UserMapper;
import com.curiosity.blog.module.Comment;
import com.curiosity.blog.module.Notification;
import com.curiosity.blog.module.Question;
import com.curiosity.blog.module.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User user) {
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
            // 回复问题
            Question dbCuestion = questionMapper.getById(comment.getParentId());
            if (dbCuestion == null) {
                throw new CustomsizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUNT);
            }
            commentMapper.incCommentCount(dbComment);
            commentMapper.insert(comment);
            // 创建通知
            createNotification(user.getId(), comment.getCommentor(), dbCuestion.getTitle(), user.getName(), NotificationTypeEnum.REPLY_COMMENT, comment.getParentId());
        } else if (comment.getType().equals(CommentTypeEnum.QUESTION.getType())) {
            // 回复问题
            Question dbCuestion = questionMapper.getById(comment.getParentId());
            if (dbCuestion == null) {
                throw new CustomsizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUNT);
            }
            commentMapper.insert(comment);
            questionMapper.incCommenCount(dbCuestion);
            createNotification(user.getId(), comment.getCommentor(), dbCuestion.getTitle(), user.getName(), NotificationTypeEnum.REPLY_QUESTION, dbCuestion.getId());
        }

    }

    private void createNotification(Long userid, Long receiver, String title, String name, NotificationTypeEnum notificationTypeEnum, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(userid);
        notification.setNotifierName(name);
        notification.setOuterTitle(title);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notificationMapper.insert(notification);
    }

    public List<CommentDto> listByQuestionIdAndType(Long id, CommentTypeEnum commentTypeEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("type", commentTypeEnum.getType());
        List<Comment> comments = commentMapper.selectByQuestionAndType(map);
        if (comments == null || comments.size() == 0) {
            return new ArrayList<>();
        }
        List<Long> userIds = comments.stream().map(comment -> comment.getCommentor()).distinct().collect(Collectors.toList());
        List<User> users = userMapper.findByListId(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDto> res = comments.stream().map(comment -> {
            CommentDto target = new CommentDto();
            BeanUtils.copyProperties(comment, target);
            target.setUser(userMap.get(target.getCommentor()));
            return target;
        }).collect(Collectors.toList());
        return res;
    }
}
