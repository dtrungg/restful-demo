package org.example.restfuldemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponse {
    private Long id;
    private String transactionId;
    private String accountNumber;
    private BigDecimal inDebt;
    private BigDecimal have;
    private LocalDateTime time;
}
