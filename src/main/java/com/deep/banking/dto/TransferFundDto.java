package com.deep.banking.dto;

public record TransferFundDto(long fromAccountId, long toAccountId, double amount) {
}
