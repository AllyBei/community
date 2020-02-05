package cn.hrs.apac.community.controller;

import cn.hrs.apac.community.dto.GithubAccessTokenDTO;
import cn.hrs.apac.community.dto.GithubUser;
import cn.hrs.apac.community.provider.GithubProvider;
import cn.hrs.apac.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse httpServletResponse
                           ) {

        GithubAccessTokenDTO githubAccessTokenDTO = new GithubAccessTokenDTO();

        //Set Github access token information from Github Developer Settings
        githubAccessTokenDTO.setClient_id(clientId);
        githubAccessTokenDTO.setState(state);
        githubAccessTokenDTO.setCode(code);
        githubAccessTokenDTO.setClient_secret(clientSecret);
        githubAccessTokenDTO.setRedirect_uri(redirectUri);

        // Transfer access token code to Github
        String githubAccessToken = githubProvider.getGithubAccessToken(githubAccessTokenDTO);

        // Get user object with token
        GithubUser githubUser = githubProvider.getUser(githubAccessToken);

        /*Set session and cookie if login successfully
        All redirect to index.html*/
        if (githubUser!=null){

           /* Set user model information (will mapping and prepare model data to insert to database)
           * from Github user information that remote api provided*/
            String customToken = userService.setUserInfo(githubUser);

            // Insert user model information into database
            userService.insertUserInfo();

            // Add cookie with <"customToken", random UUID token >
            httpServletResponse.addCookie(new Cookie("customToken",customToken));

            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }

}
