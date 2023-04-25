package com.tr.account;

import com.tr.account.dto.request.CreateCustomerRequest;
import com.tr.account.dto.response.AccountCustomerDto;
import com.tr.account.dto.response.CustomerDto;
import com.tr.account.model.Customer;

import java.util.Set;

public class CustomerFactorySupport {

    public static final String id = "CUSTOMER_ID";

    public static CreateCustomerRequest createCustomer() {
        return new CreateCustomerRequest("name", "surname","email");
    }

    public static Customer createCustomer(CreateCustomerRequest request) {
        return new Customer(request.name(), request.surname(), request.email());
    }

    public static Customer generateCustomer() {
        return new Customer(id,"name", "surname", "email", Set.of());
    }

    public static CustomerDto generateCustomerDto(Customer customer) {
        return new CustomerDto(customer.getId(),customer.getName(),customer.getSurname(),customer.getEmail(),Set.of());
    }

    public static AccountCustomerDto generateAccountCustomerDto() {
        return new AccountCustomerDto(id,"name","surname","email");
    }
}
