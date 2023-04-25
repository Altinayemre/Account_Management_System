package com.tr.account.service;

import com.tr.account.AccountFactorySupport;
import com.tr.account.CustomerFactorySupport;
import com.tr.account.TransactionFactorySupport;
import com.tr.account.dto.converter.AccountDtoConverter;
import com.tr.account.dto.request.CreateAccountRequest;
import com.tr.account.dto.response.AccountDto;
import com.tr.account.dto.response.TransactionDto;
import com.tr.account.exception.ServiceOperationException;
import com.tr.account.model.Account;
import com.tr.account.model.Customer;
import com.tr.account.model.Transaction;
import com.tr.account.repository.AccountRepository;
import com.tr.account.validation.ServiceAccountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class AccountServiceTest extends AccountFactorySupport{

    private AccountService accountService;
    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;
    private ServiceAccountValidator serviceAccountValidator;
    private Clock clock;

    @BeforeEach
     void setUp(){
        accountRepository = mock(AccountRepository.class);
        customerService = mock(CustomerService.class);
        accountDtoConverter = mock(AccountDtoConverter.class);
        serviceAccountValidator=mock(ServiceAccountValidator.class);
        clock = mock(Clock.class);

       accountService = new AccountService(
                accountRepository,
                customerService,
                accountDtoConverter,
                serviceAccountValidator,
                clock);

        when(clock.instant()).thenReturn(getCurrentInstant());
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
    }

    @Test
    void testCreateAccount_whenCustomerIdExistsAndInitialCreditMoreThanZero_shouldCreateAccountWithTransaction(){
        String customerId = CustomerFactorySupport.id;
        int initialCredit = 100;

        CreateAccountRequest createAccountRequest = AccountFactorySupport.createAccountRequest(initialCredit);

        Customer customer = CustomerFactorySupport.generateCustomer();
        Account account = AccountFactorySupport.generateAccount(createAccountRequest);
        Transaction transaction = TransactionFactorySupport.generateTransaction(
                createAccountRequest.initialCredit(),
                account
        );
        account.getTransactions().add(transaction);

        TransactionDto transactionDto = TransactionFactorySupport.generateTransactionDto(initialCredit);
        AccountDto excpectedAccountDto = AccountFactorySupport.generateAccountDto(initialCredit, Set.of(transactionDto));

        when(customerService.findCustomerById(customerId)).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);
        doNothing().when(serviceAccountValidator).checkIfInitialCredit(createAccountRequest,getLocalDateTimeNow(),account);
        when(accountDtoConverter.convert(account)).thenReturn(excpectedAccountDto);

        AccountDto actualAccountDto = accountService.createAccount(createAccountRequest);

        assertEquals(excpectedAccountDto,actualAccountDto);

        verify(customerService).findCustomerById(customerId);
        verify(accountRepository).save(account);
        verify(serviceAccountValidator).checkIfInitialCredit(createAccountRequest,getLocalDateTimeNow(),account);
        verify(accountDtoConverter).convert(account);
    }

    @Test
    void testCreateAccount_whenCustomerIdExistsAndInitialCreditIsZero_shouldCreateAccountWithoutTransaction(){
        String customerId = CustomerFactorySupport.id;
        int initialCredit = 0;

        CreateAccountRequest createAccountRequest = AccountFactorySupport.createAccountRequest(initialCredit);

        Customer customer = CustomerFactorySupport.generateCustomer();
        Account account = AccountFactorySupport.generateAccount(createAccountRequest);
        AccountDto expectedAccountDto = AccountFactorySupport.generateAccountDto(initialCredit,Set.of());

        when(customerService.findCustomerById(customerId)).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);
        doNothing().when(serviceAccountValidator).checkIfInitialCredit(createAccountRequest,getLocalDateTimeNow(),account);
        when(accountDtoConverter.convert(account)).thenReturn(expectedAccountDto);

        AccountDto actualAccountDto = accountService.createAccount(createAccountRequest);

        assertEquals(expectedAccountDto,actualAccountDto);

        verify(customerService).findCustomerById(customerId);
        verify(accountRepository).save(account);
        verify(accountDtoConverter).convert(account);
        verify(serviceAccountValidator).checkIfInitialCredit(createAccountRequest,getLocalDateTimeNow(),account);
    }

    @Test
    void testCreateAccount_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundException(){
        String customerId = CustomerFactorySupport.id;
        int initialCredit = 0;

        CreateAccountRequest createAccountRequest = AccountFactorySupport.createAccountRequest(initialCredit);

        when(customerService.findCustomerById(customerId)).thenThrow(new ServiceOperationException.NotFoundException("test-exception"));

        assertThrows(ServiceOperationException.NotFoundException.class,
                () -> accountService.createAccount(createAccountRequest));

        verify(customerService).findCustomerById(customerId);
        verifyNoInteractions(accountRepository,accountDtoConverter,serviceAccountValidator);
    }
}