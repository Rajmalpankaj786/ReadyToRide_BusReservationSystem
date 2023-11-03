package com.ReadyToRide.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ReadyToRide.Model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer>{
	public Route findByRouteFromAndRouteTo(String from, String To);
}
