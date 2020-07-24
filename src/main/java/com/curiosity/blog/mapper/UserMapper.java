package com.curiosity.blog.mapper;

import com.curiosity.blog.module.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/18
 */
@Mapper
@Repository
public interface UserMapper {
    // 插入一个用户
    void insert(User user);

    // 获取通过token获取当前用户
    User findByToken(String token);

    User findByAccountId(String accountId);

    User findById(Long creator);

    void updata(User user);

    List<User> findByListId(List<Long> userIds);
}
