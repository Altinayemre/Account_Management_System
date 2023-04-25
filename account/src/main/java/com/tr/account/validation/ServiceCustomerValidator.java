package com.tr.account.validation;

import com.tr.account.exception.BaseValidationException;
import com.tr.account.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class ServiceCustomerValidator {

    private final CustomerRepository customerRepository;

    public ServiceCustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void checkIfCustomerEmailExists(String email){
        if(this.customerRepository.existsByEmail(email)){
            throw new BaseValidationException("Email already exists: "+email);
        }
    }
}

