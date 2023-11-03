package com.ReadyToRide.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReadyToRide.Exception.RouteException;
import com.ReadyToRide.Exception.UserException;
import com.ReadyToRide.Model.Bus;
import com.ReadyToRide.Model.CUSession;
import com.ReadyToRide.Model.Route;
import com.ReadyToRide.Repository.CurrentUserSessionRepository;
import com.ReadyToRide.Repository.RouteRepository;

@Service
public class RouteService {

	@Autowired
	private RouteRepository rRepo;

	@Autowired
	private CurrentUserSessionRepository srepo;

	public Route addRoute(Route route, String key) throws RouteException, UserException {
		CUSession loggedInUser = srepo.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to add Route");
		}

		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {

			Route newRoute = rRepo.findByRouteFromAndRouteTo(route.getRouteFrom(), route.getRouteTo());

			if (newRoute != null)
				throw new RouteException(
						"Route from : " + route.getRouteFrom() + " to " + route.getRouteTo() + " already exists");

			List<Bus> busList = new ArrayList<>();
			route.setBuslist(busList);

			return rRepo.save(route);
		} else
			throw new UserException("Access denied");
	}

	public Route updateRoute(Route route, String key) throws RouteException, UserException {

		CUSession loggedInUser = srepo.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to update Route");
		}

		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {

//			Optional<User> user = uRepo.findById(loggedInUser.getUserId());

			Optional<Route> opt = rRepo.findById(route.getRouteId());

			if (opt.isPresent()) {

				Route existingRoute = opt.get();

				if (!existingRoute.getBuslist().isEmpty())
					throw new RouteException("Cannot update Route ! Already buses are Scheduled for this route");

				if (route.getDistance() != null)
					existingRoute.setDistance(route.getDistance());
				if (route.getRouteFrom() != null)
					existingRoute.setRouteFrom(route.getRouteFrom());
				if (route.getRouteTo() != null)
					existingRoute.setRouteTo(route.getRouteTo());
//					 if (route.getBuslist() != null) existingRoute.setBuslist(route.getBuslist());

				Route saved = rRepo.save(existingRoute);

				return saved;

			} else {
				throw new RouteException("No route exist to update please save the Route first");
			}

		} else
			throw new UserException("Access denied");

	}

	public Route deleteRoute(Integer routeId, String key) throws RouteException, UserException {

		CUSession loggedInUser = srepo.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to delete Route");
		}

		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {

			// Optional<User> user = uRepo.findById(loggedInUser.getUserId());

			Optional<Route> opt = rRepo.findById(routeId);

			if (opt.isPresent()) {

				Route rot = opt.get();

				if (!rot.getBuslist().isEmpty())
					throw new RouteException("Cannot delete Route ! Already buses are Scheduled for this route");

				rRepo.delete(rot);

				return rot;

			} else {
				throw new RouteException("No route found on this " + routeId + " id");
			}

		}

		throw new UserException("Access denied");

	}
	
public Route viewRoute(Integer routeId,String key) throws RouteException, UserException {
		
		CUSession loggedInUser=srepo.findByUuid(key);
		
		if(loggedInUser==null) {
			throw new UserException("Please provide a valid key to view Route");
		}	
		
//		Optional<User> user = uRepo.findById(loggedInUser.getUserId());
//		
//		if(user.isPresent() || loggedInUser.getType().equalsIgnoreCase("Admin")) {
			
			Optional<Route> opt =rRepo.findById(routeId);
			
		     if(opt.isPresent()) {
		    	 
		    	 return opt.get();
		     }
		     else {
		    	 throw new RouteException("No route found on this "+routeId+" id");
		     }
//		}
//		
//		throw new UserException("Access declained... ");
			
	}

public List<Route> viewAllRoute(String key) throws RouteException, UserException {
	
	CUSession loggedInUser=srepo.findByUuid(key);
	
	if(loggedInUser==null) {
		throw new UserException("Please provide a valid key to view Route");
	}	
	
//	Optional<User> user = uRepo.findById(loggedInUser.getUserId());
//	
//	if(user.isPresent() || loggedInUser.getType().equalsIgnoreCase("Admin")) {
		
		List<Route> routeList = rRepo.findAll();
		
		if(routeList.size()!=0) {
			
			return routeList;
		}else {
			throw new RouteException("Route list is empty");
		}
//	}
//	
//	throw new UserException("Access declained... ");
	
}


}
