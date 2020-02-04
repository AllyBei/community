package cn.hrs.apac.community.controller;

import cn.hrs.apac.community.dto.GithubAccessTokenDTO;
import cn.hrs.apac.community.dto.GithubUser;
import cn.hrs.apac.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {

        GithubAccessTokenDTO githubAccessTokenDTO = new GithubAccessTokenDTO();

        //Set Github access token information from Github Developer Settings
        githubAccessTokenDTO.setClient_id("6ab47e4c530100275865");
        githubAccessTokenDTO.setState(state);
        githubAccessTokenDTO.setCode(code);
        githubAccessTokenDTO.setClient_secret("5b3d4a11820b01df03a8527d6987c0142c63a66b");
        githubAccessTokenDTO.setRedirect_url("http://localhost:10086/callback");

        // Transfer access token code to Github
        String token = githubProvider.getGithubAccessToken(githubAccessTokenDTO);

        // Get user object with token
        GithubUser githubUser = githubProvider.getUser(token);
        System.out.println(githubUser.getLogin());

        return "index";
    }

}
