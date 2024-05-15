package com.example.LibraryManagementSystem.Controller;

import com.example.LibraryManagementSystem.DTOS.PatronDTO;
import com.example.LibraryManagementSystem.Services.PatronService;
import com.example.LibraryManagementSystem.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "patrons")
public class PatronController {

    public PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public List<PatronDTO> getAllPatrons(){
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronDTO> getPatron(@PathVariable("id") int id){
        PatronDTO PatronDTO = patronService.getPatron(id);
        return ResponseEntity.ok().body(PatronDTO);
    }

    @PostMapping
    public ResponseEntity<PatronDTO> createPatron(@RequestBody PatronDTO PatronDTO) {
        return new ResponseEntity<PatronDTO>(patronService.createPatron(PatronDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronDTO> updatePatron(@PathVariable int id, @RequestBody PatronDTO PatronDTO) {
        return new ResponseEntity<PatronDTO>(patronService.updatePatron(id, PatronDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePatron(@PathVariable int id) {
        patronService.deletePatron(id);
        ApiResponse apiResponse = new ApiResponse("Success", "Patron deleted successfully");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

}
