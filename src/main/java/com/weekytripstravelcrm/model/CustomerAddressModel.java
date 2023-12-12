package com.weekytripstravelcrm.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class CustomerAddressModel {
    private String street;
    private String apt;
    private String city;
    private String state;
    private String pinCode;
}
