package org.example.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    private Integer userId;//主键ID
    private String username;//用户名
    //    @JsonIgnore
    private String password;//密码
    //    @NotEmpty
//    @Email
//    private String email;//邮箱
//    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime lastLoginTime;//更新时间
    private LocalDateTime passwordUpdateTime;//
}
