package com.example.LibraryManagementSystem.Models;

import jakarta.persistence.*;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Table
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int borrowingRecordId;

    @Column
    private java.sql.Date borrowingDate;

    @Column
    private java.sql.Date returnDate;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    public BorrowingRecord() {
    }

    public BorrowingRecord(Date borrowingDate, Date returnDate, Book book, Patron patron) {
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.book = book;
        this.patron = patron;
    }

    public BorrowingRecord(int borrowingRecordId, Date borrowingDate, Date returnDate) {
        this.borrowingRecordId = borrowingRecordId;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public int getPatronId() {
        return this.borrowingRecordId;
    }

    public void setPatronId(int patronId) {
        this.borrowingRecordId = patronId;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }
}
