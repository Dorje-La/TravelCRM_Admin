package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Car;
import com.weekytripstravelcrm.entity.Vendor;
import com.weekytripstravelcrm.exception.CarNotFoundException;
import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.model.CarModel;
import com.weekytripstravelcrm.model.VendorModel;
import com.weekytripstravelcrm.repository.VendorRepository;
import com.weekytripstravelcrm.util.ValidateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VendorServiceTest {
    private MockMvc mockMvc;
    @Mock
    private VendorRepository vendorRepository;
    @InjectMocks
    private VendorService vendorService;
    private VendorModel vendor;
    @Mock
    private ValidateUtil validateUtil;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(vendorService).build();
    }
    @Test
    public void saveVendorData_success() throws Exception {
        VendorModel vendorModel = new VendorModel();

        vendorModel.setVendorName("A");
        vendorModel.setVendorAddress("abc");
        vendorModel.setVendorContact("123");
        vendorModel.setVendorCity("Delhi");
        vendorModel.setVendorBookingPolicy("Booking Policy");
        vendorModel.setVendorCancellationPolicy("Booking Cancellation");

        CarModel car = new CarModel();
        car.setCarNumber("AK55A");
        car.setCarModel("Toyota");
        car.setCarPhotos("image1");
        car.setCarRate(20);
        car.setCarCategory("DD");
        List<CarModel> carList = new ArrayList<>();
        carList.add(car);
        vendorModel.setCarList(carList);

        Vendor vendor1 = new Vendor();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor1);
        String result = vendorService.saveVendorsAndCars(vendorModel);
        assertEquals("Vendors and Cars saved successfully.", result);
    }

    @Test
    public void saveVendorData_failure() throws Exception {
        Assert.assertThrows(NullException.class, () -> vendorService.saveVendorsAndCars(null));
    }

    @Test
    public void findCabByVendorNameAndCity() {
        String vendorName = "Dorje";
        String vendorCity = "SanFrancisco";

        Vendor vendor = new Vendor();
        vendor.setVendorId("1");
        vendor.setVendorName(vendorName);
        vendor.setVendorAddress("SanFrancisco");
        vendor.setVendorContact("12345");
        vendor.setVendorCity(vendorCity);
        vendor.setVendorBookingPolicy("Booking policy");
        vendor.setVendorCancellationPolicy("Cancellation policy");

        Car car = new Car();
        car.setCarCategory("SUV");
        car.setCarModel("BMW");
        car.setCarNumber("097DL");
        car.setCarPhotos("img1, img2, img3");
        car.setCarRate(10.0);

        List<Car> cars = new ArrayList<>();
        cars.add(car);
        vendor.setCar(cars);

        when(vendorRepository.findByVendorNameAndVendorCity(vendorName, vendorCity)).thenReturn(vendor);
        List<CarModel> carList = vendorService.findCabByVendorNameAndCity(vendorName, vendorCity);

        System.out.println("Vendor Name: " + vendor.getVendorName());
        System.out.println("Number of Cars: " + vendor.getCar().size());
        System.out.println("Number of Cars in carList: " + carList.size());

        Assert.assertEquals(1, carList.size());
        Assert.assertEquals("BMW", carList.get(0).getCarModel());
    }

    @Test
    public void findCabByVendorNameAndCity_failure() {
        when(vendorRepository.findByVendorNameAndVendorCity("Dorje", "SanFrancisco")).thenReturn(null);
        assertThrows(CarNotFoundException.class, () -> {
            vendorService.findCabByVendorNameAndCity("Dorje", "SanFrancisco");
        });
    }

    @Test
    public void fetchVendorByCity_success() throws Exception {
        String vendorName = "Dorje";
        String vendorCity = "SanFrancisco";

        Vendor vendor = new Vendor();
        vendor.setVendorId("1");
        vendor.setVendorName(vendorName);
        vendor.setVendorAddress("SanFrancisco");
        vendor.setVendorContact("12345");
        vendor.setVendorCity(vendorCity);
        vendor.setVendorBookingPolicy("Booking policy");
        vendor.setVendorCancellationPolicy("Cancellation policy");

        Car car = new Car();
        car.setCarCategory("SUV");
        car.setCarModel("BMW");
        car.setCarNumber("097DL");
        car.setCarPhotos("img1, img2, img3");
        car.setCarRate(10.0);

        List<Car> cars = new ArrayList<>();
        cars.add(car);
        vendor.setCar(cars);

        List<Vendor> vendorList = new ArrayList<>();
        vendorList.add(vendor);

        when(vendorRepository.findByVendorCity(vendorCity)).thenReturn(vendorList);
        List<VendorModel> vendorModelList = vendorService.fetchVendorByCity(vendorCity);
        Assert.assertFalse(vendorModelList.isEmpty());

        System.out.println("Vendor Name: " + vendorModelList.get(0).getVendorName());
        System.out.println("Number of Cars: " + vendorModelList.get(0).getCarList().size());

        Assert.assertEquals("SanFrancisco", vendorModelList.get(0).getVendorCity());
    }

    @Test
    public void fetchVendorByCity_failure() throws Exception {
        when(vendorRepository.findByVendorCity("Texas")).thenReturn(null);
        assertThrows(NullException.class, () -> {
            vendorService.fetchVendorByCity("Texas");
        });
    }
}
