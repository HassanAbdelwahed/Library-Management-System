package com.example.LibraryManagementSystem.DTOS;

import lombok.Data;

import java.sql.Date;

@Data

public class PatronDTO {
    private String firstName;
    private String lastName;
    private java.sql.Date birthday;
    private String email;
    private String address;
    private String phone;
    private String gender;
    private String details;

    public PatronDTO(String firstName, String lastName, Date birthday, String email, String address, String phone, String gender, String details) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.details = details;
    }

    public PatronDTO() {
    }

}
