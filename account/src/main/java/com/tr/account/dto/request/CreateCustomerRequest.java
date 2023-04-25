package com.tr.account.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        @NotBlank(message = "name must not be empty")
        String name,
        @NotBlank(message = "surname must not be empty")
        String surname,
        @NotBlank(message = "email must not be empty")
        String email
) {
}
