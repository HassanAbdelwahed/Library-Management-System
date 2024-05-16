package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.DTOS.BookDTO;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.utils.ApiResponse;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        super();
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    @Cacheable(value="book")
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "book", key = "#bookId")
    public BookDTO getBook(int bookId) {
        if(bookRepository.existsById(bookId)) {
            Book book = bookRepository.findById(bookId).orElse(null);
            return modelMapper.map(book, BookDTO.class);
        }else {
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", bookId));
        }
    }

    public BookDTO createBook(BookDTO bookDTO) {
        if (bookDTO.getBookISBN() == null) {
            throw new DataIntegrityViolationException("Book ISBN should not be NULL");
        }
        if (bookRepository.existsBybookISBN(bookDTO.getBookISBN()))
            throw new DataIntegrityViolationException("Book ISBN should be unique");
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookRepository.save(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @CachePut(cacheNames = "book", key = "#bookId")
    public BookDTO updateBook(int bookId, BookDTO bookDTO) {
        if (bookRepository.existsById(bookId)) {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (bookDTO.getBookISBN() == null) {
                throw new DataIntegrityViolationException("Book ISBN is required");
            }
            book.setBookISBN(bookDTO.getBookISBN());
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPublicationYear(bookDTO.getPublicationYear());
            book.setCategory(bookDTO.getCategory());
            book.setPublisher(bookDTO.getPublisher());
            book = bookRepository.save(book);
            return modelMapper.map(book, BookDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", bookId));
        }
    }

    @CacheEvict(cacheNames = "book", key = "#bookId")
    public ApiResponse deleteBook(int bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return new ApiResponse("Success", "Book deleted successfully");
        } else {
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", bookId));
        }
    }
}
