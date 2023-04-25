package com.tr.account;

import com.tr.account.dto.response.TransactionDto;
import com.tr.account.model.Account;
import com.tr.account.model.Transaction;
import com.tr.account.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionFactorySupport {

    private static final LocalDateTime getLocalDateTimeNow = AccountFactorySupport.getLocalDateTimeNow();
    public static Transaction generateTransaction(BigDecimal amount, Account account){
        return new Transaction(amount,getLocalDateTimeNow,account);
    }

    public static TransactionDto generateTransactionDto(int amount){
        return new TransactionDto(
                "",
                TransactionType.INITIAL,
                new BigDecimal(amount),
                getLocalDateTimeNow);
    }
}
