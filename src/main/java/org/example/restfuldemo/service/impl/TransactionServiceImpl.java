package org.example.restfuldemo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.restfuldemo.dto.request.TransactionRequest;
import org.example.restfuldemo.dto.response.TransactionHistoryResponse;
import org.example.restfuldemo.entity.TransactionHistory;
import org.example.restfuldemo.repository.TransactionHistoryRepository;
import org.example.restfuldemo.service.TransactionService;
import org.example.restfuldemo.utils.AesImpl;
import org.example.restfuldemo.utils.RsaImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionHistoryRepository transactionHistoryRepository;
    @Value("${app.aes-key}")
    private String aesKey;

    @Value("${app.rsa-public-key}")
    private String rsaPublicKey;

    @Value("${app.rsa-private-key}")
    private String rsaPrivateKey;

    @Override
    public List<TransactionHistoryResponse> getTransactionHistory() {
        List<TransactionHistory> list = transactionHistoryRepository.findAll();
        // Convert TransactionHistory entities to TransactionHistoryResponse DTOs
        return list.stream()
                .map(transactionHistory -> {
                    TransactionHistoryResponse response = new TransactionHistoryResponse();
                    response.setId(transactionHistory.getId());
                    try {
                        response.setTransactionId(RsaImpl.encrypt(transactionHistory.getTransactionId(), rsaPublicKey));
                        response.setAccountNumber(RsaImpl.encrypt(transactionHistory.getAccountNumber(), rsaPublicKey));
                        response.setInDebt(RsaImpl.encrypt(transactionHistory.getInDebt().toPlainString(),
                                rsaPublicKey
                        ));
                        response.setHave(RsaImpl.encrypt(transactionHistory.getHave().toPlainString(), rsaPublicKey));
                        response.setTime(RsaImpl.encrypt(transactionHistory.getTime().toString(), rsaPublicKey));
                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    }
                    return response;
                }).collect(Collectors.toList());
    }

    @Override
    public List<TransactionHistoryResponse> transferMoney(TransactionRequest request) {

        TransactionHistory fromAccount = new TransactionHistory();
        //        fromAccount.setTransactionId(UUID.randomUUID().toString());
        //        fromAccount.setAccountNumber(request.getAccountNumberSrc());
        try {
            fromAccount.setAccountNumber(AesImpl.encrypt(request.getAccountNumberSrc(), aesKey));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        fromAccount.setInDebt(fromAccount.getInDebt().add(request.getAmount()));
        fromAccount.setHave(BigDecimal.ZERO);
        transactionHistoryRepository.save(fromAccount);

        TransactionHistory toAccount = new TransactionHistory();
        //        toAccount.setTransactionId(UUID.randomUUID().toString());
        try {
            toAccount.setAccountNumber(AesImpl.encrypt(request.getAccountNumberDest(), aesKey));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        toAccount.setInDebt(BigDecimal.ZERO);
        toAccount.setHave(toAccount.getHave().add(request.getAmount()));
        transactionHistoryRepository.save(toAccount);

        List<TransactionHistory> list = transactionHistoryRepository.findAll();
        // Convert TransactionHistory entities to TransactionHistoryResponse DTOs
        return list.stream()
                .map(transactionHistory -> {
                    TransactionHistoryResponse response = new TransactionHistoryResponse();
                    response.setId(transactionHistory.getId());
                    response.setTransactionId(transactionHistory.getTransactionId());
                    response.setAccountNumber(transactionHistory.getAccountNumber());
                    response.setInDebt(transactionHistory.getInDebt().toPlainString());
                    response.setHave(transactionHistory.getHave().toPlainString());
                    response.setTime(transactionHistory.getTime().toString());
                    return response;
                }).collect(Collectors.toList());
    }
}
