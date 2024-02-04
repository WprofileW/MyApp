package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer userId;//主键ID
    private String username;//用户名
    @JsonIgnore
    private String password;//密码
    private LocalDateTime createTime;//创建时间
    private LocalDateTime lastLoginTime;//更新时间
    private LocalDateTime passwordUpdateTime;//
}
