package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Customer;
import com.weekytripstravelcrm.entity.CustomerAddress;
import com.weekytripstravelcrm.exception.CustomerRecordNotFoundException;
import com.weekytripstravelcrm.model.CustomerAddressModel;
import com.weekytripstravelcrm.model.CustomerModel;
import com.weekytripstravelcrm.repository.CustomerRepository;
import com.weekytripstravelcrm.util.KeyGenerator;
import com.weekytripstravelcrm.util.ValidateUtil;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class CustomerService {
    Logger log = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ValidateUtil validateUtil;
    static KeyGenerator keyGenerator = new KeyGenerator();
    public String createCustomer(CustomerModel customerModel) throws Exception {
        if (customerModel != null) {
            validateRequest(customerModel);
            Customer customer = new Customer();
            String prefix = "CS";
            Long startingValue = 1000L;
            Long customerIdFromRepo = customerRepository.findMaxCustomerIdAsLong();
            customer.setCustomerId(KeyGenerator.generateId(prefix, customerIdFromRepo, Customer.class, startingValue));

            customer.setName(customerModel.getName());
            customer.setEmail(customerModel.getEmail());
            customer.setContact(customerModel.getContact());
            CustomerAddressModel customerAddressModel = customerModel.getCustomerAddress();
            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setApt(customerAddressModel.getApt());
            customerAddress.setCity(customerAddressModel.getCity());
            customerAddress.setStreet(customerAddressModel.getStreet());
            customerAddress.setState(customerAddressModel.getState());
            customerAddress.setPinCode(customerAddressModel.getPinCode());
            customer.setCustomerAddress(customerAddress);
            try {
                this.customerRepository.save(customer);
            } catch (Exception e) {
                log.error("Error details ::" + e.getMessage());
                throw new RuntimeException("failed to save " + e.getMessage());
            }
        } else {
            return "failed to saved";
        }
        log.info("Data Save Successfully");
        return "success";
    }
    public String updateCustomer(String customerId, CustomerModel customerModel) {
        Optional<Customer> customerObj = this.customerRepository.findById(String.valueOf(customerId));
        if (customerObj.isPresent()) {
            Customer customer = customerObj.get();
            customer.setName(customerModel.getName());
            customer.setEmail(customerModel.getEmail());
            customer.setContact(customerModel.getContact());
            CustomerAddress customerAddress = customer.getCustomerAddress();
            customerAddress.setApt(customerModel.getCustomerAddress().getApt());
            customerAddress.setCity(customerModel.getCustomerAddress().getCity());
            customerAddress.setStreet(customerModel.getCustomerAddress().getStreet());
            customerAddress.setState(customerModel.getCustomerAddress().getState());
            customerAddress.setPinCode(customerModel.getCustomerAddress().getPinCode());
            customer.setCustomerAddress(customerAddress);
            try {
                this.customerRepository.save(customer);
            } catch (Exception e) {
                log.error("Error details ::" + e.getMessage());
               throw new RuntimeException("Update failed");
            }
        }
        log.info("Data update Successfully");
        return "success";
    }
    public Customer getCustomerByIdOrEmail(String idOrEmail) {
        if (idOrEmail == null || idOrEmail.isEmpty()) {
            throw new IllegalArgumentException("ID or email cannot be null or empty");
        }

        Customer customer = null;
        if (idOrEmail.matches("\\d+")) {
            // idOrEmail is numeric, treat it as an ID
            Integer customerId = Integer.valueOf(idOrEmail);
            customer = customerRepository.findById(String.valueOf(customerId))
                    .orElseThrow(() -> new CustomerRecordNotFoundException("Customer not found with id: " + customerId, "CustomerRecordNotFoundException"));
        } else {
            // idOrEmail is not numeric, treat it as an email
            customer = customerRepository.findByEmail(idOrEmail);
            if (customer == null) {
                throw new CustomerRecordNotFoundException("Customer not found with email: " + idOrEmail, "CustomerRecordNotFoundException");
            }
        }
        return customer;
    }

    public void deleteCustomerByIdOrEmail(String idOrEmail) {
        if (idOrEmail == null || idOrEmail.isEmpty()) {
            throw new IllegalArgumentException("ID or email cannot be null or empty");
        }
        if (idOrEmail.matches("\\d+")) {
            // idOrEmail is numeric, treat it as an ID
            Integer customerId = Integer.valueOf(idOrEmail);
            if (!customerRepository.existsById(String.valueOf(customerId))) {
                throw new CustomerRecordNotFoundException("Customer not found with id: " + customerId, "CustomerRecordNotFoundException");
            }
            customerRepository.deleteById(String.valueOf(customerId));
        } else {
            // idOrEmail is not numeric, treat it as an email
            Customer customer = customerRepository.findByEmail(idOrEmail);
            if (customer == null) {
                throw new CustomerRecordNotFoundException("Customer not found with email: " + idOrEmail, "CustomerRecordNotFoundException");
            }
            customerRepository.deleteByEmail(idOrEmail);
        }
    }
    private void validateRequest(CustomerModel customerModel) throws Exception {
        validateUtil = new ValidateUtil();
        validateUtil.isValidName(customerModel.getName());
        validateUtil.isValidEmail(customerModel.getEmail());
        validateUtil.isValidMobile(customerModel.getContact());
        CustomerAddressModel customerAddressModel = customerModel.getCustomerAddress();
        validateUtil.isValidName(customerAddressModel.getCity());
        validateUtil.isValidName(customerAddressModel.getState());
    }
}









