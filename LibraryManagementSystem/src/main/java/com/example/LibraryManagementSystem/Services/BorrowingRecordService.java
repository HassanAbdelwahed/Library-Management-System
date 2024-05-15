package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Models.BorrowingRecord;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.repositories.BorrowingRecordRepository;
import com.example.LibraryManagementSystem.repositories.PatronRepository;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.time.LocalDate;


@Service
public class BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;


    @Autowired
    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, PatronRepository patronRepository) {
        super();
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Transactional
    public void borrowBook(int bookId, int patronId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book == null)
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", bookId));
        Patron patron = patronRepository.findById(patronId).orElse(null);
        if(patron == null)
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        if (book.isBorrowed())
            throw new ResourceNotFoundException("Sorry,This book is borrowed. Try again later.");

        BorrowingRecord borrowingRecord = new BorrowingRecord(
                Date.valueOf(LocalDate.now()),
                null,
                new Book(bookId),
                new Patron(patronId));
        book.setBorrowed(true);
        bookRepository.save(book);
        borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public void returnBook(int bookId, int patronId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book == null)
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", bookId));
        Patron patron = patronRepository.findById(patronId).orElse(null);
        if(patron == null)
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        if (!book.isBorrowed())
            throw new ResourceNotFoundException("Sorry,This book is not borrowed.");

        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBook_IdAndPatron_IdAndReturn_DateIsNull(bookId, patronId).orElse(null);
        if (borrowingRecord == null)
            throw new ResourceNotFoundException("Borrowing record not found.");

        borrowingRecord.setReturnDate(Date.valueOf(LocalDate.now()));
        borrowingRecordRepository.save(borrowingRecord);
        book.setBorrowed(false);
        bookRepository.save(book);
    }
}
