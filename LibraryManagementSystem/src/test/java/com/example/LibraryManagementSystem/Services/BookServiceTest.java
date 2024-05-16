package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.DTOS.BookDTO;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    private BookService bookService;
    @Mock
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        this.bookService = new BookService(this.bookRepository, this.modelMapper);
    }

    @Test
    public void createBook_fails_IfBookISBN_IsNull() {
        BookDTO bookDTO = new BookDTO(null, "Data Structure", "Mohamed",
                "2021",
                "Computer Science",
                "Ali");
        var exp = assertThrows(DataIntegrityViolationException.class, () -> bookService.createBook(bookDTO));
        assertEquals(exp.getMessage(), "Book ISBN should not be NULL");
    }

    @Test
    public void createBook_fails_IfBookISBN_IsNotUnique() {
        BookDTO bookDTO = new BookDTO("541ddll", "Data Structure", "Mohamed",
                "2021",
                "Computer Science",
                "Ali");
        Book book = new Book("541ddll", "Data Structure", "Mohamed",
                "2021",
                "Computer Science",
                "Ali");
        Mockito.when(bookRepository.existsBybookISBN(bookDTO.getBookISBN())).thenReturn(true);
        var exp = assertThrows(DataIntegrityViolationException.class, () -> bookService.createBook(bookDTO));
        assertEquals(exp.getMessage(), "Book ISBN should be unique");
    }
    @Test
    public void getBook_succeed_IfBook_exist() {
        int bookId = 1;
        Book book = new Book("541ddll", "Paradigmdldl", null,
                "2021",
                "Culture",
                "Hassan");
        BookDTO bookDTO = new BookDTO("541ddll", "Paradigmdldl", null,
                "2021",
                "Culture",
                "Hassan");
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        assertEquals(bookDTO, bookService.getBook(bookId));
    }
    @Test
    public void getBook_fails_IfBook_doesNotExist() {
        int bookId = 10;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> bookService.getBook(bookId));
        assertEquals(exp.getMessage(), String.format("Book with ID %d not found", bookId));
    }

    @Test
    public void deleteBook_fails_IfBook_doesNotExist() {
        int bookId = 10;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(bookId));
        assertEquals(exp.getMessage(), String.format("Book with ID %d not found", bookId));
    }

    @Test
    public void deleteBook_succeed() {
        int bookId = 1;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        assertEquals(bookService.deleteBook(bookId).getMessage(), "Book deleted successfully");
    }

    @Test
    public void updateBook_fails_IfBook_doesNotExist() {
        int bookId = 10;
        BookDTO bookDTO = new BookDTO("541ddll", "Paradigmdldl", null,
                "2021",
                "Culture",
                "Hassan");
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(bookId, bookDTO));
        assertEquals(exp.getMessage(), String.format("Book with ID %d not found", bookId));
    }

    @Test
    public void updateBook_Succeed() {
        int bookId = 1;
        Book book = new Book("541ddll", "Paradigmdldl", null,
                "2021",
                "Culture",
                "Hassan");
        BookDTO bookDTO = new BookDTO("541ddll", "Paradigmdldl", null,
                "2021",
                "Culture",
                "Hassan");
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        assertEquals(bookDTO, bookService.updateBook(bookId, bookDTO));
    }

    @Test
    public void getALL_Book_Succeed() {
        List<Book> books = new ArrayList<>();
        Book book = new Book("541ddll", "Paradigmdldl", null,
                "2021",
                "Culture",
                "Hassan");
        Book book2 = new Book("d5d54f1", "Structure", "ahmed",
                "2001",
                "Business",
                "Ali");
        books.add(book);
        books.add(book2);
        List<BookDTO> bookDTOS = new ArrayList<>();
        BookDTO bookDTO1 = new BookDTO("541ddll", "Paradigmdldl", null,
                "2021",
                "Culture",
                "Hassan");
        BookDTO bookDTO2 = new BookDTO("d5d54f1", "Structure", "ahmed",
                "2001",
                "Business",
                "Ali");
        bookDTOS.add(bookDTO1);
        bookDTOS.add(bookDTO2);
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(modelMapper.map(Mockito.any(Book.class), Mockito.eq(BookDTO.class)))
                .thenAnswer(invocation -> {
                    Book book1 = invocation.getArgument(0);
                    return new BookDTO(book1.getBookISBN(), book1.getTitle(), book1.getAuthor(), book1.getPublicationYear(), book1.getCategory(), book1.getPublisher());
                });
        assertEquals(bookDTOS, bookService.getAllBooks());
    }

}