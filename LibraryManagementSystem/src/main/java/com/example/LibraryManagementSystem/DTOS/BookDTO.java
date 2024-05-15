package com.example.LibraryManagementSystem.DTOS;

import lombok.Data;

@Data
public class BookDTO {

    private int bookId;
    private String bookISBN;
    private String title;
    private String author;
    private String publicationYear;
    private String category;
    private String publisher;
}
