package com.example.LibraryManagementSystem.DTOS;

import lombok.Data;

@Data

public class PatronDTO {
    private String firstName;
    private String lastName;
    private java.sql.Date birthday;
    private String email;
    private String address;
    private String phone;
    private String password;
    private String gender;
    private String details;
}
