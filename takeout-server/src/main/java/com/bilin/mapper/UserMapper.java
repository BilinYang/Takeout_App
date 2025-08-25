package com.bilin.mapper;

import com.bilin.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid=#{openid}")
    public User selectByOpenid(String openid);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user(openid, name, create_time) values (#{openid}, #{name}, #{createTime})")
    void insertUser(User user);

    @Select("select * from user where id=#{userId}")
    User selectById(Long userId);
}
