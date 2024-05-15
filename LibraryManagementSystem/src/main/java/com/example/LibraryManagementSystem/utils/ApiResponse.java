package com.example.LibraryManagementSystem.utils;

import lombok.Data;

@Data
public class ApiResponse {
    String status;
    String message;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
