package com.tr.account.dto.response;

import java.util.Set;

public record CustomerDto(
        String id,
        String name,
        String surname,
        String email,
        Set<CustomerAccountDto> accounts
) { }
