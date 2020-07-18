package com.curiosity.blog.controller;

import com.curiosity.blog.config.GitHubAuthorizeConfig;
import com.curiosity.blog.dto.AccessTokenDto;
import com.curiosity.blog.dto.GithubUserDto;
import com.curiosity.blog.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state, Model model) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(gitHubAuthorizeConfig.getClientId());
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret(gitHubAuthorizeConfig.getSecret());
        GithubUserDto userDto = gitHubProvider.githubUserDto(gitHubProvider.getAcessToken(accessTokenDto));
        System.out.println(userDto);
        return "index";
    }

}
