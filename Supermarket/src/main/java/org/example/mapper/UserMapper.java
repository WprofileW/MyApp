package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.User;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    @Insert("insert into user(username,password,role_id,create_time,password_update_time)" +
            " values(#{username},#{password},#{roleId},now(),now())")
    void add(String username, String password, Integer roleId);

    @Update("update user set last_login_time=NOW() where username=#{username}")
    void updateLastLoginTime(String username);

    @Update("update user set password=#{md5String},password_update_time=now() where user_id=#{userId}")
    void updatePwd(String md5String, Integer userId);
}
