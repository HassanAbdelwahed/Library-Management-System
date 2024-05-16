package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.DTOS.BorrowingRecordDTO;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Models.BorrowingRecord;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.repositories.BorrowingRecordRepository;
import com.example.LibraryManagementSystem.repositories.PatronRepository;
import com.example.LibraryManagementSystem.utils.ApiResponse;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


@Service
public class BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, PatronRepository patronRepository, ModelMapper modelMapper) {
        super();
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public BorrowingRecordDTO borrowBook(int bookId, int patronId) {
        if(!bookRepository.existsById(bookId))
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", bookId));
        if(!patronRepository.existsById(patronId))
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        Book book = bookRepository.findById(bookId).get();
        if (book.isBorrowed())
            throw new ResourceNotFoundException("Sorry,This book is borrowed. Try again later");

        BorrowingRecord borrowingRecord = new BorrowingRecord(
                Date.valueOf(LocalDate.now()),
                null,
                new Book(bookId),
                new Patron(patronId));
        book.setBorrowed(true);
        bookRepository.save(book);
        borrowingRecord = borrowingRecordRepository.save(borrowingRecord);
        BorrowingRecordDTO borrowingRecordDTO = modelMapper.map(borrowingRecord, BorrowingRecordDTO.class);
        borrowingRecordDTO.setBookId(bookId);
        borrowingRecordDTO.setPatronId(patronId);
        return borrowingRecordDTO;
    }

    @Transactional
    public ApiResponse returnBook(int bookId, int patronId) {
        if(!bookRepository.existsById(bookId))
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", bookId));
        Patron patron = patronRepository.findById(patronId).orElse(null);
        if(!patronRepository.existsById(patronId))
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));

        Book book = bookRepository.findById(bookId).get();
        if (!book.isBorrowed())
            throw new ResourceNotFoundException("Sorry,This book is not borrowed.");
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBook_IdAndPatron_IdAndReturn_DateIsNull(bookId, patronId).orElse(null);
        if (borrowingRecord == null)
            throw new ResourceNotFoundException("Sorry,This book is not borrowed By this Patron.");

        borrowingRecord.setReturnDate(Date.valueOf(LocalDate.now()));
        borrowingRecordRepository.save(borrowingRecord);
        book.setBorrowed(false);
        bookRepository.save(book);
        return new ApiResponse("success", "Bock returned Successfully");
    }
}
