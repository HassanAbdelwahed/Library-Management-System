package com.example.LibraryManagementSystem.Models;

import jakarta.persistence.*;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Table
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PatronId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private java.sql.Date birthday;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String password;
    @Column
    private String gender;
    @Column
    private String details;

    public Patron(int patronId, String firstName, String lastName, Date birthday, String email, String address, String phone, String password, String gender, String details) {
        PatronId = patronId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
        this.details = details;
    }

    public int getPatronId() {
        return PatronId;
    }

    public void setPatronId(int patronId) {
        PatronId = patronId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
