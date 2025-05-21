package org.example.restfuldemo.repository;

import org.example.restfuldemo.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

}
