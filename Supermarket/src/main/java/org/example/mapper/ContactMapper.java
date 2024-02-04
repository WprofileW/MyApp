package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Contact;

@Mapper
public interface ContactMapper {

    @Select("SELECT * FROM contact WHERE contact_id = #{contactId}")
    Contact getContactById(Integer contactId);

    @Select("SELECT * FROM contact WHERE name = #{name}")
    Contact getContactByName(String name);

    @Insert("INSERT INTO contact (user_id,name, email, phone, address, notes, created_at) " +
            "VALUES (#{userId},#{name}, #{email}, #{phone}, #{address}, #{notes}, NOW())")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertContact(Integer userId, String name, String phone, String email, String address, String notes);

    @Update("UPDATE contact SET name = #{name}, email = #{email}, phone = #{phone}, "
            + "address = #{address}, notes = #{notes}, created_at = #{createdAt}, updated_at = #{updatedAt} " +
            "WHERE contact_id = #{contactId}")
    void updateContact(Contact contact);

    @Delete("DELETE FROM contact WHERE contact_id = #{contactId}")
    void deleteContact(Integer contactId);

    @Select("SELECT * FROM contact WHERE user_id = #{userId}")
    Contact getContactByUserId(Integer userId);
}
