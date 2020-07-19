package com.curiosity.blog.mapper;

import com.curiosity.blog.module.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    int create(Question question);

    List<Question> list(@Param("page") Integer page,@Param("size") Integer size);

    int count();
}
