package com.tr.account.service;

import com.tr.account.CustomerFactorySupport;
import com.tr.account.dto.converter.CustomerDtoConverter;
import com.tr.account.dto.request.CreateCustomerRequest;
import com.tr.account.dto.response.CustomerDto;
import com.tr.account.exception.BaseValidationException;
import com.tr.account.exception.ServiceOperationException;
import com.tr.account.model.Customer;
import com.tr.account.repository.CustomerRepository;
import com.tr.account.validation.ServiceCustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CustomerServiceTest extends CustomerFactorySupport{

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private CustomerDtoConverter customerDtoConverter;
    private ServiceCustomerValidator serviceCustomerValidator;

    @BeforeEach
    void setUp(){
    customerDtoConverter = mock(CustomerDtoConverter.class);
    customerRepository = mock(CustomerRepository.class);
    serviceCustomerValidator = mock(ServiceCustomerValidator.class);

    customerService = new CustomerService(customerRepository,customerDtoConverter, serviceCustomerValidator);
    }

    @Test
    void testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer = CustomerFactorySupport.generateCustomer();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Customer result = customerService.findCustomerById(id);

        assertEquals(result,customer);

        verify(customerRepository).findById(id);
        verifyNoInteractions(customerDtoConverter, serviceCustomerValidator);
    }

    @Test
    void testFindByCustomerId_whenCustomerIdDoesNotExists_shouldThrowNotFoundException(){
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceOperationException.NotFoundException.class, () -> customerService.findCustomerById(id));

        verify(customerRepository).findById(id);
        verifyNoInteractions(customerDtoConverter, serviceCustomerValidator);
    }

    @Test
    void testGetAllCustomer_whenCustomersExist_shouldReturnListOfCustomerDto(){
        Customer customer1 = CustomerFactorySupport.generateCustomer();
        Customer customer2 = CustomerFactorySupport.generateCustomer();

        List<Customer> customers = Arrays.asList(customer1,customer2);

        CustomerDto customerDto1 = CustomerFactorySupport.generateCustomerDto(customer1);
        CustomerDto customerDto2 = CustomerFactorySupport.generateCustomerDto(customer2);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerDtoConverter.convert(any(Customer.class))).thenReturn(customerDto1,customerDto2);

        List<CustomerDto> customersDto = customerService.getAllCustomer();

        assertNotNull(customersDto);
        assertEquals(2,customersDto.size());

       customersDto.forEach(customerDto -> {
           assertEquals("name",customerDto.name());
           assertEquals("surname",customerDto.surname());
           assertEquals("email",customerDto.email());
       });

        verify(customerRepository).findAll();
        verify(customerDtoConverter, Mockito.times(2)).convert(Mockito.any(Customer.class));
        verifyNoInteractions(serviceCustomerValidator);
    }

    @Test
    void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomerDto(){
        Customer customer = CustomerFactorySupport.generateCustomer();
        CustomerDto customerDto = CustomerFactorySupport.generateCustomerDto(customer);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerDtoConverter.convert(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerById(id);

        assertEquals(result,customerDto);

        verify(customerRepository).findById(id);
        verify(customerDtoConverter).convert(customer);
        verifyNoInteractions(serviceCustomerValidator);
    }

    @Test
    void testGetCustomerById_whenCustomerIdDoesNotExists_shouldThrowNotFoundException(){
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        //Alternative to assertThrows
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> customerService.getCustomerById(id))
                .isInstanceOf(ServiceOperationException.NotFoundException.class)
                .hasMessageContaining("Customer could not find by id: " + id);

        verify(customerRepository).findById(id);
        verifyNoInteractions(customerDtoConverter, serviceCustomerValidator);
    }

    @Test
    void testCreateCustomer_whenCreateCustomerCalledWithValidRequest_shouldReturnCustomerDto(){
        CreateCustomerRequest createCustomerRequest = CustomerFactorySupport.createCustomer();
        Customer customer = CustomerFactorySupport.createCustomer(createCustomerRequest);
        CustomerDto expectedCustomerDto = CustomerFactorySupport.generateCustomerDto(customer);

        when(customerRepository.save(customer)).thenReturn(customer);
        doNothing().when(serviceCustomerValidator).checkIfCustomerEmailExists(customer.getEmail());
        when(customerDtoConverter.convert(customer)).thenReturn(expectedCustomerDto);

        CustomerDto actualCustomerDto = customerService.createCustomer(createCustomerRequest);

        assertEquals(expectedCustomerDto,actualCustomerDto);

        verify(serviceCustomerValidator).checkIfCustomerEmailExists(customer.getEmail());
        verify(customerRepository).save(customer);
        verify(customerDtoConverter).convert(customer);
    }

    @Test
    void testCreateCustomer_whenCreateCustomerCalledWithEmailAlreadyExistsInDatabase_shouldThrowBaseValidationException() {
        CreateCustomerRequest createCustomerRequest = CustomerFactorySupport.createCustomer();
        String email = createCustomerRequest.email();

        doThrow(new BaseValidationException("Email already exists: " + email))
                        .when(serviceCustomerValidator).checkIfCustomerEmailExists(email);

        assertThrows(BaseValidationException.class, () -> customerService.createCustomer(createCustomerRequest));

        verify(serviceCustomerValidator).checkIfCustomerEmailExists(email);
        verifyNoInteractions(customerDtoConverter, customerRepository);
    }
}