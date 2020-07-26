package com.curiosity.blog.mapper;

import com.curiosity.blog.module.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/26
 */
@Mapper
@Repository
public interface NotificationMapper {


    int insert(Notification notification);

    int countByUser(Long userId);

    List<Notification> listByPage(@Param("userId") Long userId,@Param("page") Integer spage, @Param("size") Integer size);

    Long unreadCount(Long id);

    Notification selectById(Long id);

    int updataStatus(Notification notification);
}
