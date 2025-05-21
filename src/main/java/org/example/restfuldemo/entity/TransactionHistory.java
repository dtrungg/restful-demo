package org.example.restfuldemo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "in_debt", nullable = false, precision = 19, scale = 2)
    private BigDecimal inDebt;

    @Column(name = "have", nullable = false, precision = 19, scale = 2)
    private BigDecimal have;

    @Column(name = "time", nullable = false, updatable = false)
    @CreationTimestamp // Hibernate annotation to set the current datetime on creation
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime time;
}


