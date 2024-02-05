package org.example.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Contact {
    private Integer contactId;
    private String username;
    private String realName;
    private String phone;
    private String email;
    private String address;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
