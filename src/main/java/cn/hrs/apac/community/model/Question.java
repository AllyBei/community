package cn.hrs.apac.community.model;

import lombok.Data;
import org.springframework.stereotype.Component;


// Define data model in database(hrs_community),table(question)
@Data
@Component
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}
