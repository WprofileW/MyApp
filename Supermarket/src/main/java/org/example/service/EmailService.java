package org.example.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    boolean send(String to, String title, String content);

}
