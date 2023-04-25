package com.tr.account.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "customerId must not be empty")
        String customerId,
        @Min(0)
        @NotNull(message = "initialCredit must not be empty")
        BigDecimal initialCredit
) { }
