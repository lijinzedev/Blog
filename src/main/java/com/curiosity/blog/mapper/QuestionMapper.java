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

    List<Question> list(@Param("page") Integer page,@Param("size") Integer size);

    int count();
    int countUser(Integer userId);
    List<Question>  listUser(@Param("userId") Integer userId, @Param("page") Integer spage, @Param("size")Integer size);

    Question getById(@Param("id") Integer id);
}
