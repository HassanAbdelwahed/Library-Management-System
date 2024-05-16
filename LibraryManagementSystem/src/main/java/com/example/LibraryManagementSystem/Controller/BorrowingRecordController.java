package com.example.LibraryManagementSystem.Controller;


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
@RequestMapping(path = "books")
public class BorrowingRecordController {

    public BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse> borrowBook(@PathVariable int bookId, @PathVariable int patronId) {
        borrowingRecordService.borrowBook(bookId, patronId);
        ApiResponse apiResponse = new ApiResponse("success", "Bock borrowed Successfully");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse> returnBook(@PathVariable int bookId, @PathVariable int patronId) {
        borrowingRecordService.returnBook(bookId, patronId);
        ApiResponse apiResponse = new ApiResponse("success", "Bock returned Successfully");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

}
