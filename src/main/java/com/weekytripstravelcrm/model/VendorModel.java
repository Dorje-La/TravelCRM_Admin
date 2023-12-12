package com.weekytripstravelcrm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorModel {
    private String vendorId;
    private String vendorName;
    private String vendorAddress;
    private String vendorContact;
    private String vendorCity;
    private String vendorBookingPolicy;
    private String vendorCancellationPolicy;
    private List<CarModel> carList;
}


