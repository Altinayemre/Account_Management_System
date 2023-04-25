package com.tr.account.service;

import com.tr.account.dto.converter.AccountDtoConverter;
import com.tr.account.dto.request.CreateAccountRequest;
import com.tr.account.dto.response.AccountDto;
import com.tr.account.model.Account;
import com.tr.account.model.Customer;
import com.tr.account.repository.AccountRepository;
import com.tr.account.validation.ServiceAccountValidator;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;
    private final ServiceAccountValidator serviceAccountValidator;
    private final Clock clock;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter accountDtoConverter,
                          ServiceAccountValidator serviceAccountValidator,
                          Clock clock) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
        this.serviceAccountValidator=serviceAccountValidator;
        this.clock = clock;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {

        Customer customer = this.customerService.findCustomerById(createAccountRequest.customerId());

        Account account = new Account(
                createAccountRequest.initialCredit(),
                getLocalDateTimeNow(),
                customer);

        this.serviceAccountValidator.checkIfInitialCredit(createAccountRequest,getLocalDateTimeNow(),account);

        return this.accountDtoConverter.convert(this.accountRepository.save(account));
    }

    private LocalDateTime getLocalDateTimeNow() {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(
                instant,
                Clock.systemDefaultZone().getZone()
        );
    }
}
