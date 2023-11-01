package com.ReadyToRide.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ReadyToRide.Model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
