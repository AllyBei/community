package cn.hrs.apac.community.service;

import cn.hrs.apac.community.mapper.UserMapper;
import cn.hrs.apac.community.model.Question;
import cn.hrs.apac.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class QuestionService {

    @Autowired
    private Question question;

    @Autowired
    private UserMapper userMapper;


    public User setQuestionParam(
            HttpServletRequest httpServletRequest,
            Model model){

        User user = null;

        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies == null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("customToken".equals(cookie.getName())){
                String customToken = cookie.getValue();
                user = userMapper.findByCustomToken(customToken);
                if (user != null) {
                    httpServletRequest.getSession().setAttribute("githubUser",user);
                }
                break;
            }
        }

        // Verify if user is login or not, and add error message
        if (user == null) {
            model.addAttribute("error","user not login");
        }

        return user;
    }
}
