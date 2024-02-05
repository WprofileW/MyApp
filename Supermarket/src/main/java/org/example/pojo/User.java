package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    private Integer userId;//主键ID
    private String username;//用户名
    private String password;//密码
    private LocalDateTime createTime;//创建时间
    private LocalDateTime lastLoginTime;//上次登陆时间
    private LocalDateTime passwordUpdateTime;//密码更新时间
}
