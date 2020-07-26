package com.curiosity.blog.interceptor;

import com.curiosity.blog.mapper.NotificationMapper;
import com.curiosity.blog.mapper.UserMapper;
import com.curiosity.blog.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/20
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user1 = request.getSession().getAttribute("user");
        if (user1 == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                String token = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("token"))
                        .map(cookie -> cookie.getValue()).findFirst().orElse("");
                User user = null;
                if (!StringUtils.isEmpty(token)) {
                    user = userMapper.findByToken(token);
                }
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    Long unreadCount = notificationMapper.unreadCount(user.getId());
                    request.getSession().setAttribute("unreadCount", unreadCount);

                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
