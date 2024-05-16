package com.example.LibraryManagementSystem.Models;

import jakarta.persistence.*;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;
@Entity
@Table
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PatronId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column
    private java.sql.Date birthday;
    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String gender;
    @Column
    private String details;

    public Patron() {
    }

    public Patron(int patronId, String firstName, String lastName, Date birthday, String email, String address, String phone, String gender, String details) {
        PatronId = patronId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.details = details;
    }

    public Patron(String firstName, String lastName, Date birthday, String email, String address, String phone, String gender, String details) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.details = details;
    }

    public Patron(int patronId) {
        PatronId = patronId;
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
