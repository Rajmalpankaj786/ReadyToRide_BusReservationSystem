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

	public Bus updateBus(Bus bus, String key) throws BusException, UserException {

		CUSession loggedInUser = srepo.findByUuid(key);


		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to update Bus");
		}
		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {
			Optional<Bus> opt = busdao.findById(bus.getBusId());
			if (opt.isPresent()) {
				Bus curr = opt.get();
	
				if (curr.getAvailableSeats() != curr.getSeats())
					throw new BusException("Cannot update Bus already scheduled");

				Route route = rrepo.findByRouteFromAndRouteTo(curr.getRouteFrom(), curr.getRouteTo());

				if (bus.getRouteFrom() != null && bus.getRouteTo() != null) {
					route = rrepo.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());

//		if route not found it will throw bus exception			
					if (route == null)
						throw new BusException("Invalid route details");
				}
//		setting details	
				if (bus.getArrivalTime() != null)
					curr.setArrivalTime(bus.getArrivalTime());
//				curr.setArrivalTime(bus.getArrivalTime().toString());
				if (bus.getAvailableSeats() != null)
					curr.setAvailableSeats(bus.getAvailableSeats());
				if (bus.getBusName() != null)
					curr.setBusName(bus.getBusName());
				if (bus.getBusType() != null)
					curr.setBusType(bus.getBusType());
				if (bus.getDepartureTime() != null)
					curr.setDepartureTime(bus.getDepartureTime());
				if (bus.getDriverName() != null)
					curr.setDriverName(bus.getDriverName());
				if (bus.getRouteFrom() != null)
					curr.setRouteFrom(bus.getRouteFrom());
				if (bus.getRouteTo() != null)
					curr.setRouteTo(bus.getRouteTo());
				if (bus.getSeats() != null)
					curr.setSeats(bus.getSeats());

//			save updated bus 	
				Bus updated = busdao.save(curr);
				route.getBuslist().add(updated);
				route.getBuslist().remove(bus);

				return updated;

			}
			throw new BusException("Bus with id " + bus.getBusId() + "does not exist");

		} else
			throw new UserException("Unauthorized Access! Only Admin can make changes");

	}
	public Bus deleteBus(Integer busId, String key) throws BusException, UserException {

		CUSession loggedInUser = srepo.findByUuid(key);

//		If user not logged in throw User Exception
		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to delete Bus");
		}
		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {
//			optional is used to handle null pointer exception
			Optional<Bus> opt = busdao.findById(busId);
			if (opt.isPresent()) {
				Bus exbus = opt.get();

				if (exbus.getAvailableSeats() != exbus.getSeats())
					throw new BusException("Cannot delete Bus already scheduled");

				busdao.delete(exbus);
				return exbus;
			}

			throw new BusException("bus doesn't exists with this " + busId + " id");
		} else
			throw new UserException("Unauthorized Access! Only Admin can delete Bus");

	}
}
