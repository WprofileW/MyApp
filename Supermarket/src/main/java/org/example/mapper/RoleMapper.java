package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Role;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM role WHERE role_id = #{roleId}")
    Role getRoleById(@Param("roleId") Integer roleId);

    @Select("SELECT * FROM role")
    List<Role> getAllRoles();

    @Insert("INSERT INTO role (role_name) VALUES (#{roleName})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId")
    void insertRole(Role role);

    @Update("UPDATE role SET role_name = #{roleName} WHERE role_id = #{roleId}")
    void updateRole(Role role);

    @Delete("DELETE FROM role WHERE role_id = #{roleId}")
    void deleteRoleById(@Param("roleId") Integer roleId);
}
