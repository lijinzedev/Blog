package com.curiosity.blog.service;

import com.curiosity.blog.dto.NotificationDto;
import com.curiosity.blog.dto.PaginationDto;
import com.curiosity.blog.enums.NotificationStatusEnum;
import com.curiosity.blog.enums.NotificationTypeEnum;
import com.curiosity.blog.exception.CustomizeErrorCodeImpl;
import com.curiosity.blog.exception.CustomsizeException;
import com.curiosity.blog.mapper.NotificationMapper;
import com.curiosity.blog.mapper.QuestionMapper;
import com.curiosity.blog.mapper.UserMapper;
import com.curiosity.blog.module.Notification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/25
 */

@Service
public class NotificationServie {
    @Autowired
    private NotificationMapper notificationMapper;


    public PaginationDto list(Long userId, Integer page, Integer size) {

        int totalCount = notificationMapper.countByUser(userId);
        int totalPage = 0;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) page = totalPage;
        if (page < 1) page = 1;

        int spage = (page - 1) * size;

        PaginationDto<NotificationDto> paginationDto = new PaginationDto();
        List<Notification> notifications = notificationMapper.listByPage(userId, spage, size);
        List<NotificationDto> collect = notifications.stream().map(notification -> {
            NotificationDto dto = new NotificationDto();
            BeanUtils.copyProperties(notification, dto);
            dto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            return dto;
        }).collect(Collectors.toList());
        paginationDto.setData(collect);
        paginationDto.setPagination(totalPage, page, size);
        return paginationDto;


    }

    public Long unreadCount(Long id) {
        return notificationMapper.unreadCount(id);
    }

    public NotificationDto read(Long userId, Long id) {
        Notification notification = notificationMapper.selectById(id);
        if (!notification.getReceiver().equals(userId)) {
            throw new CustomsizeException(CustomizeErrorCodeImpl.READ_NOTIFICATION_FILD);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updataStatus(notification);
        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification, notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDto;
    }
}
