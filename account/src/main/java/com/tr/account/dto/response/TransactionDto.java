package com.tr.account.dto.response;

import com.tr.account.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        String id,
        TransactionType transactionType,
        BigDecimal amount,
        LocalDateTime transactionDate
) { }
