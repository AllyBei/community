package cn.hrs.apac.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    // Map to index.html
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
