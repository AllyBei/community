package cn.hrs.apac.community.service;

import cn.hrs.apac.community.dto.GithubUser;
import cn.hrs.apac.community.mapper.UserMapper;
import cn.hrs.apac.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private User user;

    @Autowired
    private UserMapper userMapper;

    //Set user info from githubUser information

    /**
     *
     * @param githubUser Github User information that remote api return and we objected
     * @return random UUID token
     */
    public String setUserInfo(GithubUser githubUser){
        user.setLogin(githubUser.getLogin());
        user.setAccountId(String.valueOf(githubUser.getId()));
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        return token;
    }

    //Insert user info to database by mybatis(UserMapper)
    public void insertUserInfo(){
        userMapper.insert(user);
    }

    public void verifyTokenFromCookies(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null){
            return;
        }
        for (Cookie cookie : cookies) {
            if ("customToken".equals(cookie.getName())){
                String customToken = cookie.getValue();
                User user = userMapper.findByCustomToken(customToken);
                if (user != null) {
                    httpServletRequest.getSession().setAttribute("githubUser",user);
                }
                break;
            }
        }

    }

}
