package com.ReadyToRide.Model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer busId;

	private String busName;

	private String driverName;

	private String busType;

	private String routeFrom;

	private String routeTo;

	private String arrivalTime;

	private String departureTime;

	private Integer seats;

	private Integer availableSeats;

	@ManyToOne
	@JsonIgnore
	private Route route;

	

}
