package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    //发件人邮箱
    public String user;
    //发件人邮箱授权码
    public String code;
    //发件人邮箱对应的服务器域名
    public String host;
    //身份验证开关
    private boolean auth;
}