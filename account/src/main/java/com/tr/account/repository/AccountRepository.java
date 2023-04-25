package com.tr.account.repository;

import com.tr.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account ,String> {
}
