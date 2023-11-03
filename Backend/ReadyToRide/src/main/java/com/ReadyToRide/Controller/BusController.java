package com.ReadyToRide.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ReadyToRide.Exception.BusException;
import com.ReadyToRide.Exception.UserException;
import com.ReadyToRide.Model.Bus;
import com.ReadyToRide.Service.BusService;

import lombok.extern.slf4j.Slf4j;




//this class is generating APIs for different  method.
@RestController
@CrossOrigin("*")
@RequestMapping("Bus")
@Slf4j
public class BusController {

//	@Autowired annotation can be used to autowire bean on the setter method 
	
	@Autowired
	private BusService busService;
	
//	adding bus by post method by providing bus details and authorization key
	
	@PostMapping("/add")
	public ResponseEntity<Bus> addBusHandler(@RequestBody Bus bus,@RequestParam String key)throws BusException, UserException{
		
		Bus savedBus = busService.addBus(bus,key);
		
		return new ResponseEntity<Bus>(savedBus,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Bus> updateBusHandler(@RequestBody Bus bus,@RequestParam String key)throws BusException, UserException{
		
		Bus updatedBus = busService.updateBus(bus,key);
		
		return new ResponseEntity<Bus>(updatedBus,HttpStatus.ACCEPTED);
		
	}
	@DeleteMapping("/delete/{busId}")
	public ResponseEntity<Bus> deleteBusHandler(@PathVariable("busId") Integer busId,@RequestParam String key)throws BusException, UserException{
		
		Bus deletedBus = busService.deleteBus(busId,key);
		
		return new ResponseEntity<Bus>(deletedBus,HttpStatus.OK);
	}
	@GetMapping("/view/{busId}")
	public ResponseEntity<Bus> viewBusHandler(@PathVariable("busId") Integer busId,@RequestParam String key) throws BusException, UserException{
		
		Bus busById = busService.viewBus(busId,key);
		
		return new ResponseEntity<Bus>(busById,HttpStatus.FOUND);
		
	}
	}