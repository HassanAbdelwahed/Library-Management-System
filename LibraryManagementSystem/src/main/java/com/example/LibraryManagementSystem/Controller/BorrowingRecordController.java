package com.example.LibraryManagementSystem.Controller;


import com.example.LibraryManagementSystem.DTOS.BorrowingRecordDTO;
import com.example.LibraryManagementSystem.Services.BorrowingRecordService;
import com.example.LibraryManagementSystem.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api")
public class BorrowingRecordController {

    public BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> borrowBook(@PathVariable int bookId, @PathVariable int patronId) {
        return ResponseEntity.ok(borrowingRecordService.borrowBook(bookId, patronId));
    }

    @PostMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse> returnBook(@PathVariable int bookId, @PathVariable int patronId) {
        return new ResponseEntity<ApiResponse>(borrowingRecordService.returnBook(bookId, patronId), HttpStatus.OK);
    }

}
