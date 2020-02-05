package cn.hrs.apac.community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

// Define data model in database(hrs_community),table(user)
@Data
@Component
public class User {
    private Integer id;
    private String accountId;
    private String login;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}
