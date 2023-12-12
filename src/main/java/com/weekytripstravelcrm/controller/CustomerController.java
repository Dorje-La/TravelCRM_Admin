package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.entity.Customer;
import com.weekytripstravelcrm.model.CustomerModel;
import com.weekytripstravelcrm.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/customer")
@SecurityRequirement(name = "test-swagger")
public class CustomerController {
    Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;
    @PostMapping(value = "/saveData",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveData(@RequestBody CustomerModel customerModel) throws Exception {
        log.info("customer saved API");
        String message = this.customerService.createCustomer(customerModel);
        return message;
    }
    @PutMapping(value = "/updateCustomer/{customerId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateCustomer(@PathVariable String Id, @RequestBody CustomerModel customerModel) {
        log.info("CustomerData update API");
        return this.customerService.updateCustomer(Id, customerModel);
    }



    @GetMapping("/{idOrEmail}")
    public Customer getCustomer(@PathVariable String idOrEmail) {

        try {
            if(idOrEmail.matches("\\d+")) {
                return customerService.getCustomerByIdOrEmail((idOrEmail));
            } else {
                return customerService.getCustomerByIdOrEmail(idOrEmail);
            }
        } catch (Exception ex) {
            log.error("Error fetching customer: " + ex.getMessage());
            throw ex;
        }
    }
    @DeleteMapping("/delete/{idOrEmail}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String idOrEmail) {
        try {
            if(idOrEmail.matches("\\d+")) {
                customerService.deleteCustomerByIdOrEmail((idOrEmail));
                return ResponseEntity.ok("Customer deleted");
            } else {
                customerService.deleteCustomerByIdOrEmail(idOrEmail);
                return ResponseEntity.ok("Customer deleted");
            }
        } catch (Exception ex) {
            log.error("Error deleting customer: " + ex.getMessage());
            throw ex;
        }
    }
}
