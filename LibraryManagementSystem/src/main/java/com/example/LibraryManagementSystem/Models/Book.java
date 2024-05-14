package com.example.LibraryManagementSystem.Models;

import jakarta.persistence.*;

@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    @Column
    private String bookISBN;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String publicationYear;
    @Column
    private String category;
    @Column
    private int amount;
    @Column
    private String publisher;

    public Book(int bookId, String bookISBN, String title, String author, String publicationYear, String category, int amount, String publisher) {
        this.bookId = bookId;
        this.bookISBN = bookISBN;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.category = category;
        this.amount = amount;
        this.publisher = publisher;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
