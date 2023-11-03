package com.ReadyToRide.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ReadyToRide.Model.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer>{

	public List<Bus> findByBusType(String busType);
}
