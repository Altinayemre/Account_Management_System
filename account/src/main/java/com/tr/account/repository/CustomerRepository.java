package com.tr.account.repository;

import com.tr.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {
     boolean existsByEmail(String email);
}
