package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.model.HotelModel;
import com.weekytripstravelcrm.service.HotelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @authore Chirenjeebi
 * The Hotel Controller class create api for save, update, fetch and delete hotel records.
 */

@RestController
@RequestMapping(value = "/hotel")
@SecurityRequirement(name = "test-swagger")
public class HotelController {

	Logger log = LoggerFactory.getLogger(HotelController.class);
	@Autowired
	private HotelService hotelService;

	/**
	 *
	 * @param hotelModel
	 * @return saved hotel information into the hotel table
	 */
	@PostMapping(value = "/saveHotelInfo")
	public String saveHotelInfo(@RequestBody HotelModel hotelModel) {
		String message = this.hotelService.saveHotel(hotelModel);
		log.info("Successfully saved hotel information from saveHotelInfo" );
		return message;
	}


//	@GetMapping(value="/displayHotel/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public HotelModel displayHotelById(@PathVariable("hotelId") long hotelId) {
//		HotelModel hotelModel = this.hotelService.getHotelByHotelId(hotelId);
//		log.info("Successfully get hotel information from displayHotelById" );
//        return hotelModel;
//	}

	@GetMapping(value="/getHotelById", produces = MediaType.APPLICATION_JSON_VALUE)
	public Hotel getByHotelId(@RequestParam("hotelId") String hotelId) {
		Hotel hotel = this.hotelService.getByHotelId(hotelId);
		log.info("Successfully get hotel information from displayHotelById" );
		return hotel;
	}

	/**
	 *
	 * @param hotelName
	 * @return single hotel details by name
	 */
	@GetMapping(value="/getHotelByName")
	public Hotel displayHotelByName(@RequestParam(name="hotelName") String hotelName) {
		Hotel hotel = this.hotelService.getHotelByName(hotelName);
		log.info("Successfully get hotel information from displayHotelByName" );
        return hotel;
	}

	/**
	 *
	 * @param city
	 * @return  list of hotels by city
	 */
	@GetMapping(value="/getHotelByCity")
	public List<Hotel> getHotelByCity(@RequestParam(name="city") String city) {
		List<Hotel> hotelModelList = this.hotelService.getHotelByCity(city);
		log.info("Successfully get hotel information from getHotelByCity" );
		return  hotelModelList;
	}

	@GetMapping("/displayAllHotels")
	public List<HotelModel> displayAllHotels() {
		List<HotelModel> hotelModelList = this.hotelService.getALlHotels();
		log.info("Successfully get hotel information from displayAllHotels" );
		return  hotelModelList;
	}

	@PutMapping("updateHotel/{id}")
	public String updateHotel(@PathVariable("id") String id, @RequestBody HotelModel hotelModel){
		String message = this.hotelService.updateHotelById(id, hotelModel);
		log.info("Successfully saved hotel information from updateHotel " +id);
        return message;
	}

	@DeleteMapping()
	public  String deleteHotel(@RequestParam("id") String id){
		String message = this.hotelService.deleteHotelById(id);
		log.info("Successfully saved hotel information from updateHotel " + id);
        return message;
	}

}
