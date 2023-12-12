package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.model.CarModel;
import com.weekytripstravelcrm.model.VendorModel;
import com.weekytripstravelcrm.service.VendorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vendor")
@SecurityRequirement(name = "test-swagger")
public class VendorController {

    Logger log = LoggerFactory.getLogger(VendorController.class);
    @Autowired
    private VendorService vendorService;

    /**
     * * http://localhost:8082/vendor/saveVendors
     * The API saves the data when JSON format is passed with Vendor Details
     *
     * @param vendorModel
     * @return String message
     */
    @PostMapping(value = "/saveVendors", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveVendors(@RequestBody VendorModel vendorModel) {
        try {
            String message = vendorService.saveVendorsAndCars(vendorModel);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while saving data: " + e.getMessage());
        }
    }

    /**
     * //http://localhost:8082/weeky-trip-travel/vendor/carList?vendorName=XYZ&vendorCity=NewDelhi
     * This API helps to find the specific car details when vendor name and vendor city is passed by user
     * @param vendorName
     * @param cityName
     * @return carModelList car details
     */
    @GetMapping(value = "/carList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CarModel> carList(@RequestParam("vendorName") String vendorName, @RequestParam("vendorCity") String cityName) {
        List<CarModel> carModelList = new ArrayList<>();
        if (vendorName == null || cityName == null) {
            if (vendorName == null) {
                throw new NullException("Vendor name is null\n Please enter Vendor name");
            } else if (cityName == null) {
                throw new NullException("city name is null\n Please enter city name");
            }
            return carModelList;
        } else {
            carModelList = vendorService.findCabByVendorNameAndCity(vendorName, cityName);
            return carModelList;
        }
    }


    /**
     * //http://localhost:8082/weeky-trip-travel/vendor/fetchVendorByCity?vendorCity=NewDelhi
     * This API helps to find the specific Vendor details when Car name and car model is passed by user
     *
     * @param vendorCity
     * @return vendorModelList vendor details
     */
    @GetMapping(value = "/fetchVendorByCity")
    //http://localhost:8081/vendors/fetchVendorByCity?vendorCity=test/pokhara
    public List<VendorModel> fetchVendorByCity(@RequestParam(name = "vendorCity") String vendorCity) {
        List<VendorModel> vendorModelList = new ArrayList<>();
        if (vendorCity == null) {
            throw new NullException("Vendor city is null \nPlease enter all the required fields");
        } else {
            vendorModelList = vendorService.fetchVendorByCity(vendorCity);
            return vendorModelList;
        }
    }
}
