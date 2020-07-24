package com.curiosity.blog.mapper;

import com.curiosity.blog.module.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CommentMapper {
    int insert(Comment comment);

    Comment selectByParentId(Long parentId);

    List<Comment> selectByQuestionAndType(Map<String, Object> map);
}
