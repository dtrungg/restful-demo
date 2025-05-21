package org.example.restfuldemo.service;

import org.example.restfuldemo.dto.request.TransactionRequest;
import org.example.restfuldemo.dto.response.TransactionHistoryResponse;

import java.util.List;

public interface TransactionService {
    public List<TransactionHistoryResponse> getTransactionHistory();

    public List<TransactionHistoryResponse> transferMoney(TransactionRequest request);
}
