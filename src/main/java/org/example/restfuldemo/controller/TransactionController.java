package org.example.restfuldemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.restfuldemo.dto.request.TransactionRequest;
import org.example.restfuldemo.dto.response.ResponseData;
import org.example.restfuldemo.dto.response.ResponseUtil;
import org.example.restfuldemo.dto.response.TransactionHistoryResponse;
import org.example.restfuldemo.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    // create getAllTransactionHistory method
    @GetMapping
    public ResponseEntity<ResponseData<List<TransactionHistoryResponse>>> getAllTransactionHistory() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(transactionService.getTransactionHistory(),
                        "Transaction history list successful"
                ));
    }

    // create transferMoney method
    @PostMapping("/transfer")
    public ResponseEntity<ResponseData<List<TransactionHistoryResponse>>> transferMoney(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(transactionService.transferMoney(request),
                        "Transaction history list successful"
                ));
    }

}
