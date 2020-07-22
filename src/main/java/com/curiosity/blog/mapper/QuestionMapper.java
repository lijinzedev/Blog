package com.curiosity.blog.mapper;

import com.curiosity.blog.module.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    int create(Question question);

    List<Question> list(@Param("page") Integer page, @Param("size") Integer size);

    int count();

    int countUser(Long userId);

    List<Question> listUser(@Param("userId") Long userId, @Param("page") Integer spage, @Param("size") Integer size);

    Question getById(@Param("id") Long id);

    int updateViewCount(Question question);

    void updataById(Question question);

    int incCommenCount(Question question);
}

