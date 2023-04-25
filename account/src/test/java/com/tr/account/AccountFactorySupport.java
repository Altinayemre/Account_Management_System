package com.tr.account;

import com.tr.account.dto.request.CreateAccountRequest;
import com.tr.account.dto.response.AccountCustomerDto;
import com.tr.account.dto.response.AccountDto;
import com.tr.account.dto.response.TransactionDto;
import com.tr.account.model.Account;
import com.tr.account.model.Customer;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

public class AccountFactorySupport {
    private static AccountCustomerDto customerDto = CustomerFactorySupport.generateAccountCustomerDto();
    private static Customer customer = CustomerFactorySupport.generateCustomer();
    public static final String id ="ACCOUNT_ID";

    public static Instant getCurrentInstant(){
        String instantExpected ="2023-04-18T02:20:00Z";
        Clock clock= Clock.fixed(Instant.parse(instantExpected),Clock.systemDefaultZone().getZone());
        return Instant.now(clock);
    }

    public static LocalDateTime getLocalDateTimeNow(){
        return LocalDateTime.ofInstant(getCurrentInstant(), Clock.systemDefaultZone().getZone());
    }

    public static CreateAccountRequest createAccountRequest(String customerId,int initialCredit){
        return new CreateAccountRequest(customerId,new BigDecimal(initialCredit));
    }

    public static CreateAccountRequest createAccountRequest(int initialCredit){
        return createAccountRequest(CustomerFactorySupport.id,initialCredit);
    }

    public static Account generateAccount(CreateAccountRequest createAccountRequest){
        return new Account(createAccountRequest.initialCredit(),getLocalDateTimeNow(),customer);
    }

    public static AccountDto generateAccountDto(int balance, Set<TransactionDto> transactions){
       return new AccountDto(id,new BigDecimal(balance),getLocalDateTimeNow(),customerDto,transactions);
    }
}
