package com.tr.account.dto.converter;

import com.tr.account.dto.response.AccountDto;
import com.tr.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountDtoConverter {

    private final CustomerDtoConverter customerDtoConverter;
    private final TransactionDtoConverter transactionDtoConverter;
    public AccountDtoConverter(CustomerDtoConverter customerDtoConverter,
                               TransactionDtoConverter transactionDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;
        this.transactionDtoConverter = transactionDtoConverter;
    }

    public AccountDto convert(Account account){
        return new AccountDto(account.getId(),
                account.getBalance(),
                account.getCreationDate(),
                this.customerDtoConverter.convertToAccountCustomer(Optional.ofNullable(account.getCustomer())),
                Objects.requireNonNull(account.getTransactions())
                        .stream()
                        .map(this.transactionDtoConverter::convert)
                        .collect(Collectors.toSet()));
    }


}
