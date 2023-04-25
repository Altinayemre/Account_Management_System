package com.tr.account.service;

import com.tr.account.dto.converter.CustomerDtoConverter;
import com.tr.account.dto.request.CreateCustomerRequest;
import com.tr.account.dto.response.CustomerDto;
import com.tr.account.exception.ServiceOperationException;
import com.tr.account.model.Customer;
import com.tr.account.repository.CustomerRepository;
import com.tr.account.validation.ServiceCustomerValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;
    private final ServiceCustomerValidator serviceCustomerValidator;

    public CustomerService(CustomerRepository customerRepository,
                           CustomerDtoConverter customerDtoConverter,
                           ServiceCustomerValidator serviceCustomerValidator) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
        this.serviceCustomerValidator = serviceCustomerValidator;
    }

    protected Customer findCustomerById(String id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new ServiceOperationException.NotFoundException("Customer could not find by id: " + id));

    }

    public List<CustomerDto> getAllCustomer(){
        return this.customerRepository.findAll()
                .stream()
                .map(customerDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(String customerId) {
        return this.customerDtoConverter.convert(findCustomerById(customerId));
    }

    public CustomerDto createCustomer(CreateCustomerRequest createCustomerRequest){

        Customer customer = new Customer(
                createCustomerRequest.name(),
                createCustomerRequest.surname(),
                createCustomerRequest.email());

        this.serviceCustomerValidator.checkIfCustomerEmailExists(customer.getEmail());

        return this.customerDtoConverter.convert(this.customerRepository.save(customer));
    }
}
