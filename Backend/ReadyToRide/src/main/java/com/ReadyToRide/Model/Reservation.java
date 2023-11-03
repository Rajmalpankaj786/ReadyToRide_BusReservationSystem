package com.ReadyToRide.Model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;

	private String reservationStatus;

	private String reservationType;

	@Future(message = "Date should not be in past *")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate reservationDate;

	private String reservationTime;

	private String source;

	private String destination;

	@OneToOne
	private Bus bus;

	
	
	

	
}
