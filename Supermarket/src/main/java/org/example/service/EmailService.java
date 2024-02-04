package org.example.service;

public interface EmailService {

    boolean send(String to, String title, String content);

}
