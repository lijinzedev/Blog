package com.curiosity.blog.service;

import com.curiosity.blog.dto.PaginationDto;
import com.curiosity.blog.dto.QuestionDto;
import com.curiosity.blog.mapper.QuestionMapper;
import com.curiosity.blog.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/19
 */
@Service
public class QuestionService {
    private final static int count = 7;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDto list(Integer page, Integer size) {
        int totalCount = questionMapper.count();
        int totalPage = 0;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) page = totalPage;
        if (page < 1) page = 1;

        int spage = (page - 1) * size;
        List<QuestionDto> collect = questionMapper.list(spage, size).stream().map(question ->
                {
                    QuestionDto dto = new QuestionDto();
                    BeanUtils.copyProperties(question, dto);
                    dto.setUser(userMapper.findById(question.getCreator()));
                    return dto;
                }
        ).collect(Collectors.toList());
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setQuestionList(collect);

        paginationDto.setPagination(totalPage, page, size);
        return paginationDto;
    }

    public PaginationDto list(Integer userId, Integer page, Integer size) {
        int totalCount = questionMapper.countUser(userId);
        int totalPage = 0;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) page = totalPage;
        if (page < 1) page = 1;

        int spage = (page - 1) * size;
        List<QuestionDto> collect = questionMapper.listUser(userId,spage, size).stream().map(question ->
                {
                    QuestionDto dto = new QuestionDto();
                    BeanUtils.copyProperties(question, dto);
                    dto.setUser(userMapper.findById(question.getCreator()));
                    return dto;
                }
        ).collect(Collectors.toList());
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setQuestionList(collect);

        paginationDto.setPagination(totalPage, page, size);
        return paginationDto;


    }
}
