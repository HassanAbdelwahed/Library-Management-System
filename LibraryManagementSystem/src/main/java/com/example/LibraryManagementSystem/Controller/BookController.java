package com.example.LibraryManagementSystem.Controller;

import com.example.LibraryManagementSystem.DTOS.BookDTO;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "books")
public class BookController {
    public BookService bookService;
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") int id){
        BookDTO bookDTO = bookService.getBook(id);
        return ResponseEntity.ok().body(bookDTO);
    }
    @PostMapping
    public ResponseEntity<BookDTO> createPost(@RequestBody BookDTO bookDTO) {

    }

}
