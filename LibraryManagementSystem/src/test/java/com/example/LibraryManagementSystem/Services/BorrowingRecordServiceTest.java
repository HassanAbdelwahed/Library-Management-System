package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Models.BorrowingRecord;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.repositories.BorrowingRecordRepository;
import com.example.LibraryManagementSystem.repositories.PatronRepository;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private PatronRepository patronRepository;
    @Mock
    private ModelMapper modelMapper;
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    void setUp() {
        this.borrowingRecordService = new BorrowingRecordService(this.borrowingRecordRepository, this.bookRepository, this.patronRepository, this.modelMapper);
    }

    @Test
    public void borrowBook_fails_IfBook_doesNotExist() {
        int bookId = 20, patronId = 5;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.borrowBook(bookId, patronId));
        assertEquals(exp.getMessage(), String.format("Book with ID %s not found", bookId));
    }

    @Test
    public void borrowBook_fails_IfPatron_doesNotExist() {
        int bookId = 5, patronId = 5;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.borrowBook(bookId, patronId));
        assertEquals(exp.getMessage(), String.format("Patron with ID %s not found", patronId));
    }

    @Test
    public void borrowBook_fails_IfBook_isBorrowed() {
        int bookId = 5, patronId = 5;
        Book book = new Book("541ddll", "Data Structure", "Mohamed",
                "2021",
                "Computer Science",
                "Ali");
        book.setBorrowed(true);
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        var exp = assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.borrowBook(bookId, patronId));
        assertEquals(exp.getMessage(), "Sorry,This book is borrowed. Try again later");
    }

    @Test
    public void returnBook_fails_IfBook_doesNotExist() {
        int bookId = 20, patronId = 5;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.returnBook(bookId, patronId));
        assertEquals(exp.getMessage(), String.format("Book with ID %s not found", bookId));
    }

    @Test
    public void returnBook_fails_IfPatron_doesNotExist() {
        int bookId = 5, patronId = 5;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.returnBook(bookId, patronId));
        assertEquals(exp.getMessage(), String.format("Patron with ID %s not found", patronId));
    }

    @Test
    public void borrowBook_fails_IfBook_isNotBorrowed_ByThisPatron() {
        int bookId = 5, patronId = 5;
        Book book = new Book("541ddll", "Data Structure", "Mohamed",
                "2021",
                "Computer Science",
                "Ali");
        book.setBorrowed(true);
        BorrowingRecord borrowingRecord = new BorrowingRecord(Date.valueOf("2024-05-16"), null, new Book(bookId), new Patron(patronId));
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(borrowingRecordRepository.findByBook_IdAndPatron_IdAndReturn_DateIsNull(bookId, patronId)).thenReturn(Optional.empty());
        var exp = assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.returnBook(bookId, patronId));
        assertEquals(exp.getMessage(), "Sorry,This book is not borrowed By this Patron.");
    }

    @Test
    public void returnBook_Succeed() {
        int bookId = 5, patronId = 5;
        Book book = new Book("541ddll", "Data Structure", "Mohamed",
                "2021",
                "Computer Science",
                "Ali");
        BorrowingRecord borrowingRecord = new BorrowingRecord(Date.valueOf("2024-05-16"), null, new Book(bookId), new Patron(patronId));
        book.setBorrowed(true);
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(borrowingRecordRepository.findByBook_IdAndPatron_IdAndReturn_DateIsNull(bookId, patronId)).thenReturn(Optional.of(borrowingRecord));
        assertEquals(borrowingRecordService.returnBook(bookId, patronId).getMessage(), "Bock returned Successfully");
    }
}
