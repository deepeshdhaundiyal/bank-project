package com.deep.banking.dto;

import java.time.LocalDateTime;

public record TransactionDto(long id, long accountId, double amount, String transactionType, LocalDateTime localDateTime){
}
