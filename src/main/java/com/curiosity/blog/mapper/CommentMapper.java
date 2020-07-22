package com.curiosity.blog.mapper;

import com.curiosity.blog.module.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper {
    int insert(Comment comment);

    Comment selectByParentId(Long parentId);
}
