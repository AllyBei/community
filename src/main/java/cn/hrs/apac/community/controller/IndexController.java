package cn.hrs.apac.community.controller;

import cn.hrs.apac.community.mapper.UserMapper;
import cn.hrs.apac.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    // Map to index.html
    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest) {

        // Search cookie in database to determine if need to re-login
        userService.verifyTokenFromCookies(httpServletRequest);

        return "index";
    }
}
