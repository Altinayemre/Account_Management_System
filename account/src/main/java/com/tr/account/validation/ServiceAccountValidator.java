package com.tr.account.validation;

import com.tr.account.dto.request.CreateAccountRequest;
import com.tr.account.model.Account;
import com.tr.account.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ServiceAccountValidator {

    public void checkIfInitialCredit(CreateAccountRequest createAccountRequest,
                                     LocalDateTime localDateTime,
                                     Account account) {

        if (createAccountRequest.initialCredit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(createAccountRequest.initialCredit(), localDateTime, account);
            account.getTransactions().add(transaction);
        }
    }
}
