package com.example.LibraryManagementSystem.Controller;

import com.example.LibraryManagementSystem.DTOS.BookDTO;
import com.example.LibraryManagementSystem.Services.BookService;
import com.example.LibraryManagementSystem.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/books")
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
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return new ResponseEntity<BookDTO>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<BookDTO>(bookService.updateBook(id, bookDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable int id) {
        return new ResponseEntity<ApiResponse>(bookService.deleteBook(id), HttpStatus.OK);
    }
}
