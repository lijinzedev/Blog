package com.curiosity.blog.service;

import com.curiosity.blog.dto.PaginationDto;
import com.curiosity.blog.dto.QuestionDto;
import com.curiosity.blog.exception.CustomsizeException;
import com.curiosity.blog.mapper.QuestionMapper;
import com.curiosity.blog.mapper.UserMapper;
import com.curiosity.blog.module.Question;
import com.curiosity.blog.module.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.curiosity.blog.exception.CustomizeErrorCodeImpl.QUESTION_NOT_FOUNT;

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

    public PaginationDto list(Integer page, Integer size, String tag) {
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
        List<QuestionDto> collect = questionMapper.list(spage, size, tag).stream().map(question ->
                {
                    QuestionDto dto = new QuestionDto();
                    BeanUtils.copyProperties(question, dto);
                    dto.setUser(userMapper.findById(question.getCreator()));
                    return dto;
                }
        ).collect(Collectors.toList());
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setData(collect);

        paginationDto.setPagination(totalPage, page, size);
        return paginationDto;
    }

    public PaginationDto list(Long userId, Integer page, Integer size) {
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
        List<QuestionDto> collect = questionMapper.listUser(userId, spage, size).stream().map(question ->
                {
                    QuestionDto dto = new QuestionDto();
                    BeanUtils.copyProperties(question, dto);
                    dto.setUser(userMapper.findById(question.getCreator()));
                    return dto;
                }
        ).collect(Collectors.toList());
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setData(collect);

        paginationDto.setPagination(totalPage, page, size);
        return paginationDto;


    }

    public QuestionDto getById(Long id) {
        Question question = questionMapper.getById(id);
        if (question == null) {
            throw new CustomsizeException(QUESTION_NOT_FOUNT);
        }
        QuestionDto dto = new QuestionDto();
        BeanUtils.copyProperties(question, dto);
        User user = userMapper.findById(question.getCreator());
        dto.setUser(user);
        return dto;
    }

    public void incView(Long id) {
        Question question = questionMapper.getById(id);
        questionMapper.updateViewCount(question);
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            questionMapper.create(question);
        } else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.updataById(question);
        }
    }

    public List<QuestionDto> selectRelated(QuestionDto questionDto) {
        if (StringUtils.isEmpty(questionDto.getTag())) {
            return new ArrayList<>();
        }
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTag(Arrays.stream(questionDto.getTag().split(",")).collect(Collectors.joining("|")));
        return questionMapper.selectRelated(question).stream().map(QuestionDto::convert).collect(Collectors.toList());

    }
}
