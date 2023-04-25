package com.tr.account.dto.converter;

import com.tr.account.dto.response.AccountCustomerDto;
import com.tr.account.dto.response.CustomerDto;
import com.tr.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {

    private final CustomerAccountDtoConverter customerAccountDtoConverter;

    public CustomerDtoConverter(CustomerAccountDtoConverter customerAccountDtoConverter) {
        this.customerAccountDtoConverter = customerAccountDtoConverter;
    }

    public CustomerDto convert(Customer customer){

        return new CustomerDto(customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getEmail(),
                customer.getAccounts()
                        .stream()
                        .map(this.customerAccountDtoConverter::convert)
                        .collect(Collectors.toSet()));
    }

    public AccountCustomerDto convertToAccountCustomer(Optional<Customer> customer) {
        return customer.map(c -> new AccountCustomerDto(
                c.getId(),
                c.getName(),
                c.getSurname(),
                c.getEmail())).orElseThrow(null);
    }

}
