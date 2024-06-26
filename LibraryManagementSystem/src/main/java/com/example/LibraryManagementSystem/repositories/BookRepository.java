package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Override
    List<Book> findAll();

    Boolean existsBybookISBN(String bookISBN);
}
