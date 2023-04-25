package com.tr.account.dto.response;

public record AccountCustomerDto(
        String id,
        String name,
        String surname,
        String email
) {
}
