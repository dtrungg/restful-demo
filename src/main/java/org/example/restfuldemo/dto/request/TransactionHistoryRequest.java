package org.example.restfuldemo.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionHistoryRequest {
    @NotBlank(message = "Source account number cannot be blank")
    private String accountNumberSrc;

    @NotBlank(message = "Destination account number cannot be blank")
    private String accountNumberDest;

    @NotNull(message = "Amount in debt cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Amount in debt must be greater than 0")
    private BigDecimal inDebt;

    @NotNull(message = "Amount have cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Amount have must be greater than 0")
    private BigDecimal have;
}
