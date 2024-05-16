package com.example.LibraryManagementSystem.DTOS;

import lombok.Data;

@Data
public class BookDTO  {

    private int bookId;
    private String bookISBN;
    private String title;
    private String author;
    private String publicationYear;
    private String category;
    private String publisher;

    public BookDTO(String bookISBN, String title, String author, String publicationYear, String category, String publisher) {
        this.bookISBN = bookISBN;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.category = category;
        this.publisher = publisher;
    }

    public BookDTO() {
    }
}
