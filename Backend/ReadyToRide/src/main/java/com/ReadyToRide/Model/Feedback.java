package com.ReadyToRide.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feedbackId;
	
	@Min(value = 1, message = "driverRating should be between 1 to 10")
	@Max(value = 10, message = "driverRating should be between 1 to 10")
	private Integer driverRating;
	
	@Min(value = 1, message = "serviceRating should be between 1 to 10")
	@Max(value = 10, message = "serviceRating should be between 1 to 10")
	private Integer serviceRating;
	
	@Min(value = 1, message = "overallRating should be between 1 to 10")
	@Max(value = 10, message = "overallRating should be between 1 to 10")
	private Integer overallRating;
	
	private String comments;
	
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@PastOrPresent(message = "Date should be current date or past date")
	private LocalDate feedbackDate = LocalDate.now();
	
	@JsonIgnore
	@OneToOne
	private User user;
	
	@JsonIgnore
	@OneToOne
	private Bus bus;
}
