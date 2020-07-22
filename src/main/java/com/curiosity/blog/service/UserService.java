package com.curiosity.blog.service;

import com.curiosity.blog.mapper.UserMapper;
import com.curiosity.blog.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/21
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdata(User user) {
        User curUser = userMapper.findByAccountId(user.getAccountId());
        if (curUser==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            user.setId(curUser.getId());
            user.setGmtModify(System.currentTimeMillis());
            userMapper.updata(user);
        }
    }
}
