package com.example.LibraryManagementSystem.utils;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}