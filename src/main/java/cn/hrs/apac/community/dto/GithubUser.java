package cn.hrs.apac.community.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
/* Define github use information from API
https://api.github.com/user?access_token=[Personal Access Token]*/
public class GithubUser {

    private Long id;
    private String bio;
    private String login;

}
