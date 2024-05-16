package com.example.LibraryManagementSystem.DTOS;


import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Models.Patron;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
public class BorrowingRecordDTO {

    private int borrowingRecordId;
    private java.sql.Date borrowingDate;
    private java.sql.Date returnDate;
    private int bookId;
    private int patronId;

    public BorrowingRecordDTO(Date borrowingDate, Date returnDate, int bookId, int patronId) {
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.bookId = bookId;
        this.patronId = patronId;
    }
}
