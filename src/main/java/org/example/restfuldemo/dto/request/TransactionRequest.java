package org.example.restfuldemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionRequest {
    @NotBlank(message = "Source account number cannot be blank")
    private String accountNumberSrc;

    @NotBlank(message = "Destination account number cannot be blank")
    private String accountNumberDest;

    @NotNull(message = "Amount cannot be null")
//    private BigDecimal amount;
    private String  amount;
}
