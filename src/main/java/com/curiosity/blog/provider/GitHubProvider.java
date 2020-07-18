package com.curiosity.blog.provider;

import com.alibaba.fastjson.JSON;
import com.curiosity.blog.dto.AccessTokenDto;
import com.curiosity.blog.dto.GithubUserDto;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/17
 */
@Component
public class GitHubProvider {
    public String getAcessToken(AccessTokenDto dto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));

        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String[] split1 = split[0].split("=");
            String token = split1[1];
            System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDto githubUserDto(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            GithubUserDto userDto = JSON.parseObject(response.body().string()
                    , GithubUserDto.class);
            return userDto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}