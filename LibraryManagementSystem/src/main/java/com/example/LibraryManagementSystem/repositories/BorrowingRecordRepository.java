package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.Models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {
    @Query(value = "SELECT * FROM BorrowingRecord a WHERE a.book_id = :bookId AND a.patron_id = :patronId AND return_date = null", nativeQuery = true)
    Optional<BorrowingRecord> findByBook_IdAndPatron_IdAndReturn_DateIsNull(int bookId, int patronId);
}
