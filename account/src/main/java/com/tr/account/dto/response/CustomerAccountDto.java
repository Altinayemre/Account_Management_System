package com.tr.account.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record CustomerAccountDto(
        String id,
        BigDecimal balance,
        LocalDateTime creationDate,
        Set<TransactionDto> transactions
) {
}
