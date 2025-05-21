package org.example.restfuldemo.service;

import org.example.restfuldemo.dto.response.TransactionHistoryResponse;

public interface TransactionHistoryService {
    public TransactionHistoryResponse getTransactionHistory();
    public TransactionHistoryResponse transferMoney();
}
