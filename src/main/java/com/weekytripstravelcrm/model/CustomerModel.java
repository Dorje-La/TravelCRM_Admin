package com.weekytripstravelcrm.model;

import com.weekytripstravelcrm.entity.CustomerAddress;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Getter
@Setter
public class CustomerModel {
    private String name;
    private String email;
    private String contact;
    private CustomerAddressModel customerAddress;
}
