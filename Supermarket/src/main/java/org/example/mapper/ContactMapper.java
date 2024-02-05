package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Contact;

@Mapper
public interface ContactMapper {
    @Insert("INSERT INTO " +
            "contact (username,real_name,  phone, email,address, notes, created_at) " +
            "VALUES (#{username},#{realName}, #{phone},#{email}, #{address}, #{notes}, NOW())")
    void insertContact(String username, String realName, String phone, String email, String address, String notes);

    @Update("UPDATE contact SET " +
            "real_name = #{realName},phone = #{phone}, email = #{email},address = #{address}, notes = #{notes}, updated_at =NOW()" +
            "WHERE username = #{username}")
    void updateContact(String username, String realName, String phone, String email, String address, String notes);

    @Select("SELECT * FROM contact WHERE username = #{username}")
    Contact getContactByUsername(String username);
}