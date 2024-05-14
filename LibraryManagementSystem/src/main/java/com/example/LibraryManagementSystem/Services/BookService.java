package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.DTOS.BookDTO;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository) {
        super();
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }
    public BookDTO getBook(int bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book != null) {
            return modelMapper.map(book, BookDTO.class);
        }else {
            throw new ResourceNotFoundException(String.format("User with ID %s not found", bookId));
        }
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookRepository.save(book);
    }

}
