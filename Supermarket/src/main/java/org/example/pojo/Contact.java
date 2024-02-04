package org.example.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Contact {
    private Integer contactId;
    private Integer userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
