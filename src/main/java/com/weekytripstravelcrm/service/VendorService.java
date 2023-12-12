package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Car;
import com.weekytripstravelcrm.util.KeyGenerator;
import com.weekytripstravelcrm.entity.Vendor;
import com.weekytripstravelcrm.exception.CarNotFoundException;
import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.model.CarModel;
import com.weekytripstravelcrm.model.VendorModel;
import com.weekytripstravelcrm.repository.CarRepository;
import com.weekytripstravelcrm.repository.VendorRepository;
import com.weekytripstravelcrm.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorService {
    private final Logger log = LoggerFactory.getLogger(VendorService.class);
    @Autowired
    public ValidateUtil validationUtility;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private VendorRepository vendorRepository;

    public String saveVendorsAndCars(VendorModel vendorModel) throws Exception {
        if(vendorModel != null){
            Vendor vendor = new Vendor();
            log.info("Vendor object has been saved");
            String prefix = "VN";
            Long startingValue = 1000L;
            Long vendorIdFromRepo = vendorRepository.findMaxVendorIdAsLong();
            validateVendorModel(vendorModel);
            vendor.setVendorId(KeyGenerator.generateId(prefix, vendorIdFromRepo, Vendor.class, startingValue));
            vendor.setVendorName(vendorModel.getVendorName());
            vendor.setVendorAddress(vendorModel.getVendorAddress());
            vendor.setVendorContact(vendorModel.getVendorContact());
            vendor.setVendorCity(vendorModel.getVendorCity());
            vendor.setVendorBookingPolicy(vendorModel.getVendorBookingPolicy());
            vendor.setVendorCancellationPolicy(vendorModel.getVendorCancellationPolicy());
            List<CarModel> carModelList = vendorModel.getCarList();
            List<Car> carList = new ArrayList<>();

            for (CarModel carModel : carModelList) {
                Car car = new Car();
                if (validationUtility.carCheck(carModel.getCarNumber(), carModel.getCarCategory(),
                        carModel.getCarModel(), carModel.getCarPhotos(), carModel.getCarRate())) {
                    car.setCarCategory(carModel.getCarCategory());
                    car.setCarModel(carModel.getCarModel());
                    car.setCarNumber(carModel.getCarNumber());
                    car.setCarPhotos(carModel.getCarPhotos());
                    car.setCarRate(carModel.getCarRate());
                    car.setVendor(vendor);
                }
                carList.add(car);
            }
            vendor.setCar(carList);
            vendorRepository.save(vendor);
            return "Vendors and Cars saved successfully.";
        }else{
            throw new NullException("Vendors and cars not found!!!");
        }
    }

    /**
     * Validates the provided vendor registration data.
     *
     * @param vendorModel The customer registration data to be validated.
     * @throws Exception If any validation checks fail.
     */
    public void validateVendorModel(VendorModel vendorModel) throws Exception {
        validationUtility.stringNullCheck(vendorModel.getVendorName());
        validationUtility.stringNullCheck(vendorModel.getVendorAddress());
        validationUtility.stringNullCheck(vendorModel.getVendorCity());
        validationUtility.stringNullCheck(vendorModel.getVendorContact());
        validationUtility.stringNullCheck(vendorModel.getVendorBookingPolicy());
        validationUtility.stringNullCheck(vendorModel.getVendorCancellationPolicy());
    }

    /**
     * This findCabByVendorNameAndCity helps to find the specific Cab details
     * when the user pass vendor name and the city
     *
     * @param vendorName
     * @param vendorCity
     * @return carModelList cab Details
     */
    public List<CarModel> findCabByVendorNameAndCity(String vendorName, String vendorCity) {
        log.info("Finding cab based on Vendor Name and City");
        List<CarModel> carModelList = new ArrayList<>();
        log.debug("Vendor Name: " + vendorName + " Vendor City: " + vendorCity);

        Vendor vendor = vendorRepository.findByVendorNameAndVendorCity(vendorName, vendorCity);

        if (vendor != null) {
            for (Car car : vendor.getCar()) {
                CarModel carModel = new CarModel();
                carModel.setCarCategory(car.getCarCategory());
                carModel.setCarModel(car.getCarModel());
                carModel.setCarNumber(car.getCarNumber());
                carModel.setCarPhotos(car.getCarPhotos());
                carModel.setCarRate(car.getCarRate());
                carModelList.add(carModel);
            }
            log.debug("Number of carModel: " + carModelList.size());
            return carModelList;
        } else {
            throw new CarNotFoundException();
        }
    }

    /**
     * This findVendor by City helps to find the all car details
     * when the user select carModel and the vendor detail display by city
     *
     * @param
     * @param vendorCity
     * @return carModelList car Details
     */
    public List<VendorModel> fetchVendorByCity(String vendorCity) {
        List<VendorModel> vendorModelList = new ArrayList<>();
        List<Vendor> vendorList = vendorRepository.findByVendorCity(vendorCity);
        if (vendorList != null && !vendorList.isEmpty()) {
            List<CarModel> carModelList = new ArrayList<>();
            for (Vendor vendor : vendorList) {
                VendorModel vendorModel = new VendorModel();
                vendorModel.setVendorName(vendor.getVendorName());
                vendorModel.setVendorAddress(vendor.getVendorAddress());
                vendorModel.setVendorCity(vendor.getVendorCity());
                vendorModel.setVendorContact(vendor.getVendorContact());
                vendorModel.setVendorBookingPolicy(vendor.getVendorBookingPolicy());
                vendorModel.setVendorCancellationPolicy(vendor.getVendorCancellationPolicy());
                for (Car car : vendor.getCar()) {
                    CarModel carModel = new CarModel();
                    carModel.setCarCategory(car.getCarCategory());
                    carModel.setCarModel(car.getCarModel());
                    carModel.setCarNumber(car.getCarNumber());
                    carModel.setCarPhotos(car.getCarPhotos());
                    carModel.setCarRate(car.getCarRate());
                    carModelList.add(carModel);
                }
                vendorModel.setCarList(carModelList);
                vendorModelList.add(vendorModel);
            }
            return vendorModelList;
        } else {
            throw new NullException("Vendor not found");
        }
    }
}
