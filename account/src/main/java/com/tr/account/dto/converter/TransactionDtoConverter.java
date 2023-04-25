package com.tr.account.dto.converter;

import com.tr.account.dto.response.TransactionDto;
import com.tr.account.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoConverter {

    public TransactionDto convert(Transaction transaction){
        return new TransactionDto(transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getLocalDateTime());
    }
}
