package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.Models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Integer> {
}
