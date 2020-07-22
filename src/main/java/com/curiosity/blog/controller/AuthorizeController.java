package com.curiosity.blog.controller;

import com.curiosity.blog.config.GitHubAuthorizeConfig;
import com.curiosity.blog.dto.AccessTokenDto;
import com.curiosity.blog.dto.GithubUserDto;
import com.curiosity.blog.module.User;
import com.curiosity.blog.provider.GitHubProvider;
import com.curiosity.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/16
 */

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    private GitHubAuthorizeConfig gitHubAuthorizeConfig;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           Model model,
                           HttpServletRequest request,
                           HttpServletResponse response
    ) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(gitHubAuthorizeConfig.getClientId());
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret(gitHubAuthorizeConfig.getSecret());
        GithubUserDto userDto = gitHubProvider.githubUserDto(gitHubProvider.getAcessToken(accessTokenDto));
        if (userDto != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(userDto.getName());
            user.setAccountId(String.valueOf(userDto.getId()));
            user.setAvatarUrl(userDto.getAvatar_url());
            userService.createOrUpdata(user);
            // 登录成功 写入cookie 和 session
            response.addCookie(new Cookie("token", token));
            request.getSession().setAttribute("user", user);

        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().removeAttribute("user");
        response.addCookie(new Cookie("token", ""));
        return "redirect:/";
    }
}
