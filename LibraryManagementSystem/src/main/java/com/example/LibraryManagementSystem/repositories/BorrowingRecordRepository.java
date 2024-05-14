package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.Models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {
}
