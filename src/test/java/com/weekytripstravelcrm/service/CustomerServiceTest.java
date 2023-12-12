package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Customer;
import com.weekytripstravelcrm.entity.CustomerAddress;
import com.weekytripstravelcrm.exception.CustomerRecordNotFoundException;
import com.weekytripstravelcrm.model.CustomerAddressModel;
import com.weekytripstravelcrm.model.CustomerModel;
import com.weekytripstravelcrm.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    private MockMvc mockMvc;
    @InjectMocks
    private CustomerService customerService;
    private CustomerModel customerModel;
    private Customer customer;
    private CustomerAddress customerAddress;
    private CustomerAddressModel customerAddressModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerService).build();
        customerModel = new CustomerModel();
        customerModel.setName("Asis");
        customerModel.setEmail("asis@gmail.com");
        customerModel.setContact("8765432765");
        customerAddressModel = new CustomerAddressModel();
        customerAddressModel.setStreet("1001 w walnut");
        customerAddressModel.setApt("101");
        customerAddressModel.setPinCode("98765");
        customerModel.setCustomerAddress(customerAddressModel);
        customer = new Customer();
        customer = new Customer();
        customer.setName(customerModel.getName());
        customer.setEmail(customerModel.getEmail());
        customer.setContact(customerModel.getContact());
        customerAddress.setPinCode(customerModel.getCustomerAddress().getPinCode());
        customer.setCustomerAddress(customerAddress);
    }

    @Test
    public void saveCustomerInfo_success() throws Exception {
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        String msg = this.customerService.createCustomer(customerModel);
        assertEquals(msg, "success");
    }

    @Test
    public void saveCustomer_failure() throws Exception {
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(null);
        String msg = this.customerService.createCustomer(customerModel);
        Assert.assertNotEquals(msg, "Failed to save");
    }

    @Test
    public void saveCustomer_throwsException() throws Exception {
        when(customerRepository.save(Mockito.any(Customer.class))).thenThrow(RuntimeException.class);
        Assert.assertThrows(RuntimeException.class, () -> customerService.createCustomer(customerModel));
        Assert.assertThrows(RuntimeException.class, () -> customerService.createCustomer(customerModel));
    }

    @Test
    public void testUpdateCustomer_success() throws Exception {
        // Create a sample CustomerModel object with updated data
        customerModel.setName("Ram");
        customerModel.setEmail("ram@gmail.com");
        // Mock repository to return existing customer for given ID
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        String msg = this.customerService.updateCustomer("1", customerModel);
        // Call method under test
        customerService.updateCustomer("1", customerModel);
        assertEquals(msg, "success");
    }

    @Test
    public void testUpdateCustomer_failure() throws Exception {
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(null);
        String msg = this.customerService.updateCustomer("1", customerModel);
        Assert.assertNotEquals(msg, "Failed to update");
    }

    @Test
    public void UpdateCustomer_throwsException() throws Exception {
        when(customerRepository.save(Mockito.any(Customer.class))).thenThrow(RuntimeException.class);
        when(customerRepository.findById(null)).thenThrow(RuntimeException.class);
        Assert.assertThrows(RuntimeException.class, () -> customerService.updateCustomer(null, customerModel));
    }

    @Test
    void testGetCustomerById() {
        Integer customerId = 1;
        Customer expectedCustomer = new Customer();
        when(customerRepository.findById(String.valueOf(customerId))).thenReturn(Optional.of(expectedCustomer));
        Customer result = customerService.getCustomerByIdOrEmail(customerId.toString());
        assertEquals(expectedCustomer, result);
    }

    @Test
    void testGetCustomerByEmail() {
        String customerEmail = "test1@example.com";
        Customer expectedCustomer = new Customer();
        when(customerRepository.findByEmail(customerEmail)).thenReturn(expectedCustomer);
        Customer result = customerService.getCustomerByIdOrEmail(customerEmail);
        assertEquals(expectedCustomer, result);
    }

    @Test
    void testGetCustomerByIdNotFound() {
        Integer customerId = 1;
        when(customerRepository.findById(String.valueOf(customerId))).thenReturn(Optional.empty());
        assertThrows(CustomerRecordNotFoundException.class, () -> customerService.getCustomerByIdOrEmail(customerId.toString()));
    }

    @Test
    void testGetCustomerByEmailNotFound() {
        String customerEmail = "noemail@gmail.com";
        when(customerRepository.findByEmail(customerEmail)).thenReturn(null);
        assertThrows(CustomerRecordNotFoundException.class, () -> customerService.getCustomerByIdOrEmail(customerEmail));
    }

    @Test
    void testDeleteCustomerById() {
        Integer customerId = 1;
        when(customerRepository.existsById(String.valueOf(customerId))).thenReturn(true);
        customerService.deleteCustomerByIdOrEmail(customerId.toString());
        verify(customerRepository, times(1)).deleteById(String.valueOf(customerId));
    }

    @Test
    void testDeleteCustomerByIdNotFound() {
        Integer customerId = 1;
        when(customerRepository.existsById(String.valueOf(customerId))).thenReturn(false);
        assertThrows(CustomerRecordNotFoundException.class, () -> customerService.deleteCustomerByIdOrEmail(customerId.toString()));
    }

}


