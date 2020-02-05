package cn.hrs.apac.community.mapper;

import cn.hrs.apac.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//Mapper to user model
@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (account_id,login,token,gmt_create,gmt_modified) VALUES (#{accountId},#{login},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("SELECT token from user WHERE token=#{customToken}")
    User findByCustomToken(@Param("customToken") String customToken);
}
