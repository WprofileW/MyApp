package org.example.controller;

import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    //注入email配置信息实体类
    @Autowired
    private EmailService emailService;

    //测试方法
    @RequestMapping("/send")
    public Boolean send() {
        //收件人信箱
        String to = "jiangf338@gmail.com";
//        String to = "1799743697@qq.com";
        //邮件标题
        String title = "测试邮件";
        //邮件正文
        String content = "这是一个测试邮件";
        //发送邮件
        boolean flag = emailService.send(to, title, content);
        return flag;
    }

}
