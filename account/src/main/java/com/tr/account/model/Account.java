package com.tr.account.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id ="";
    BigDecimal balance = BigDecimal.ZERO;
    LocalDateTime creationDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    Set<Transaction> transactions = new HashSet<>();

    //updated
    public Account(BigDecimal balance, LocalDateTime creationDate, Customer customer) {
        this.id="";
        this.balance=balance;
        this.creationDate=creationDate;
        this.customer=customer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Account account = (Account) object;

        if (!Objects.equals(id, account.id)) return false;
        if (!Objects.equals(balance, account.balance)) return false;
        if (!Objects.equals(creationDate, account.creationDate)) return false;
        if (!Objects.equals(customer, account.customer)) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }
}

