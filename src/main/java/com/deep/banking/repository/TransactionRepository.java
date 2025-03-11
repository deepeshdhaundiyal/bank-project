package com.deep.banking.repository;

import com.deep.banking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdOrderByTimeStampDesc(Long accountId);
}
