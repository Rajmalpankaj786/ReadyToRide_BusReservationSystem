package com.ReadyToRide.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userLoginId;
	
	@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", flags = Flag.CASE_INSENSITIVE, message = "Email must be in a valid format")
	private String email;
	
	@Size(min = 6, message = "Password length must be between 6 to 10 character")
	@Size(max = 10, message = "Password length must be between 6 to 10 character")
	private String password;

	private String firstName;

	private String lastName;
	
	@NotBlank
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone Number should be exactly ten digits")
	private String contact;

	@JsonIgnore
	@OneToOne
	private Reservation reservation;

}
