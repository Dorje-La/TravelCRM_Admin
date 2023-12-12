package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.entity.Customer;
import com.weekytripstravelcrm.exception.CustomerRecordNotFoundException;
import com.weekytripstravelcrm.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
         MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCustomerById() {
        Integer customerId = 1;
        Customer expectedCustomer = new Customer();
        when(customerService.getCustomerByIdOrEmail(customerId.toString())).thenReturn(expectedCustomer);
        Customer result = customerController.getCustomer(customerId.toString());
        assertEquals(expectedCustomer, result);
    }

    @Test
    void testDeleteCustomerById() {
        Integer customerId = 1;
        ResponseEntity<String> response = customerController.deleteCustomer(customerId.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted", response.getBody());
        // Verify that the service method was called with the correct ID
        verify(customerService).deleteCustomerByIdOrEmail(customerId.toString());
    }

    @Test
    void testGetCustomerByEmail() {
        String customerEmail = "test1@example.com";
        Customer expectedCustomer = new Customer();
        when(customerService.getCustomerByIdOrEmail(customerEmail)).thenReturn(expectedCustomer);
        Customer result = customerController.getCustomer(customerEmail);
        assertEquals(expectedCustomer, result);
    }

    @Test
    void testDeleteCustomerByEmail() {
        String customerEmail = "test1@example.com";
        ResponseEntity<String> response = customerController.deleteCustomer(customerEmail);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted", response.getBody());
        // Verify that the service method was called with the correct email.
        verify(customerService).deleteCustomerByIdOrEmail(customerEmail);
    }


    @Test
    void testGetCustomerByIdNotFound() {
        Integer customerId = 1;
        when(customerService.getCustomerByIdOrEmail(customerId.toString())).thenThrow(new CustomerRecordNotFoundException());

        // Act and Assert
        assertThrows(CustomerRecordNotFoundException.class, () -> customerController.getCustomer(customerId.toString()));
    }
    @Test
    void testGetCustomerByEmailNotFound() {
        String customerEmail = "nocustomer@gmail.com";
        when(customerService.getCustomerByIdOrEmail(customerEmail)).thenThrow(new CustomerRecordNotFoundException());

        // Act and Assert
        assertThrows(CustomerRecordNotFoundException.class, () -> customerController.getCustomer(customerEmail));
    }


    }




