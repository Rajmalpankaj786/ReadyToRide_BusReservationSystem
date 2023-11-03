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

import com.ReadyToRide.Exception.RouteException;
import com.ReadyToRide.Exception.UserException;
import com.ReadyToRide.Model.Route;
import com.ReadyToRide.Service.RouteService;

@RestController
@CrossOrigin("*")
@RequestMapping("/route")
public class RouteController {

	@Autowired
	private RouteService rService;

	@PostMapping("/add")
	public ResponseEntity<Route> addRouteHandler(@RequestBody Route route, @RequestParam String key)
			throws RouteException, UserException {

		Route rot = rService.addRoute(route, key);

		return new ResponseEntity<Route>(rot, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Route> updateRouteHandler(@RequestBody Route route, @RequestParam String key)
			throws RouteException, UserException {

		Route rot = rService.updateRoute(route, key);

		return new ResponseEntity<Route>(rot, HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/delete/{routeId}")
	public ResponseEntity<Route> deleteRouteHandler(@PathVariable("routeId") Integer routeId, @RequestParam String key)
			throws RouteException, UserException {

		Route rot = rService.deleteRoute(routeId, key);

		return new ResponseEntity<Route>(rot, HttpStatus.OK);

	}

	@GetMapping("/view/{routeId}")
	public ResponseEntity<Route> viewRouteHandler(@PathVariable("routeId") Integer routeId, @RequestParam String key)
			throws RouteException, UserException {

		Route rot = rService.viewRoute(routeId, key);

		return new ResponseEntity<Route>(rot, HttpStatus.FOUND);
	}

	@GetMapping("/viewall")
	public ResponseEntity<List<Route>> viewAllRouteHandler(@RequestParam String key)
			throws RouteException, UserException {

		List<Route> rot = rService.viewAllRoute(key);

		return new ResponseEntity<List<Route>>(rot, HttpStatus.FOUND);

	}
}
