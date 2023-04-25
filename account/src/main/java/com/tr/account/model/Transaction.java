package com.tr.account.model;

import com.tr.account.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
         @Id
         @GeneratedValue(generator = "UUID")
         @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
         String id;
         TransactionType transactionType;
         BigDecimal amount;
         LocalDateTime localDateTime;
         @ManyToOne(fetch = FetchType.LAZY, optional = false)
         @JoinColumn(name="account_id", nullable = false)
         Account account;

         public Transaction(BigDecimal amount,LocalDateTime localDateTime, Account account){
                this.id = null;
                this.transactionType=TransactionType.INITIAL;
                this.amount=amount;
                this.localDateTime=localDateTime;
                this.account=account;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(transactionType,that.transactionType)) return false;
        if (!Objects.equals(amount, that.amount)) return false;
        if (!Objects.equals(localDateTime, that.localDateTime)) return false;
        if (!Objects.equals(account, that.account)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (localDateTime != null ? localDateTime.hashCode() : 0);
        return result;
    }
}
