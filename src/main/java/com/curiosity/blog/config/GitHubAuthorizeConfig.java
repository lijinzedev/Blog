package com.curiosity.blog.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/18
 */

@ConfigurationProperties(prefix = "github")
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitHubAuthorizeConfig {

    private String clientId;
    private String secret;
    private String redirectUrl;
}
