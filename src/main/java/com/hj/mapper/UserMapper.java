package com.hj.mapper;

import com.hj.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{userName}")
    User findByName(String userName);

    @Update("update user set photo=#{url} where username=#{userName}")
    void updateUserPhoto(String userName ,String url);

    @Insert("insert into user (username,photo) values (#{username},#{url})")
    void addUser(String username,String url);
}
