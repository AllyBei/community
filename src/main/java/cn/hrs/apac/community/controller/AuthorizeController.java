package cn.hrs.apac.community.controller;

import cn.hrs.apac.community.dto.GithubAccessTokenDTO;
import cn.hrs.apac.community.dto.GithubUser;
import cn.hrs.apac.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest httpServletRequest) {

        GithubAccessTokenDTO githubAccessTokenDTO = new GithubAccessTokenDTO();

        //Set Github access token information from Github Developer Settings
        githubAccessTokenDTO.setClient_id(clientId);
        githubAccessTokenDTO.setState(state);
        githubAccessTokenDTO.setCode(code);
        githubAccessTokenDTO.setClient_secret(clientSecret);
        githubAccessTokenDTO.setRedirect_uri(redirectUri);

        // Transfer access token code to Github
        String token = githubProvider.getGithubAccessToken(githubAccessTokenDTO);

        // Get user object with token
        GithubUser githubUser = githubProvider.getUser(token);

        /*Set session and cookie if login sucessfully
        All redirect to index.html*/
        if (githubUser!=null){
            httpServletRequest.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }

}
