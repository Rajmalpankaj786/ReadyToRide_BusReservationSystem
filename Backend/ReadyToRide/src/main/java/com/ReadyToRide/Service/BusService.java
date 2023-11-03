package com.ReadyToRide.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReadyToRide.Exception.BusException;
import com.ReadyToRide.Exception.UserException;
import com.ReadyToRide.Model.Bus;
import com.ReadyToRide.Model.CUSession;
import com.ReadyToRide.Model.Route;
import com.ReadyToRide.Repository.BusRepository;
import com.ReadyToRide.Repository.CurrentUserSessionRepository;
import com.ReadyToRide.Repository.RouteRepository;

//this method is implementing bus service interface
@Service
public class BusService {

	@Autowired
	private BusRepository busdao;

	@Autowired
	private CurrentUserSessionRepository srepo;

	@Autowired
	private RouteRepository rrepo;



	public Bus addBus(Bus bus, String key) throws BusException, UserException {

		CUSession loggedInUser = srepo.findByUuid(key);

//		if user is not login it will throw exception
		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to add Bus");
		}

//		if user is Admin then you are allowed to add bus

		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {
//			finding route is present or not
			Route route = rrepo.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());
			System.out.println(bus);
			System.out.println(route);
			if (route != null) {
//				if contains bus with same details throw bus exception
				if (route.getBuslist().contains(bus)) {
					throw new BusException("Bus already exists");
				}

//				if not present then add bus to buslist
				route.getBuslist().add(bus);
				bus.setRoute(route);
				return busdao.save(bus);
			} else
				throw new BusException("Cannot find route for adding Bus");

		} else
			throw new UserException("Unauthorized Access! Only Admin can add bus");

	}

}
