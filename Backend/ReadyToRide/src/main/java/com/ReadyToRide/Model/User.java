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

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userLoginId;

	@Column(unique = true)
	private String userName;

	@Size(min = 6, message = "Password length must be between 6 to 10 character")
	@Size(max = 10, message = "Password length must be between 6 to 10 character")
	private String password;

	private String firstName;

	private String lastName;
	@NotBlank
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone Number should be exactly ten digits")
	private String contact;

	@Email(regexp = "[a-z0-9._]+@[a-z0-9._]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE, message = "Email must be in a valid format")
	private String email;

	@JsonIgnore
	@OneToOne
	private Reservation reservation;

	public Integer getUserLoginId() {
		return userLoginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public User(Integer userLoginId, String userName,
			@Size(min = 6, message = "Password length must be between 6 to 10 character") @Size(max = 10, message = "Password length must be between 6 to 10 character") String password,
			String firstName, String lastName,
			@NotBlank @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone Number should be exactly ten digits") String contact,
			@Email(regexp = "[a-z0-9._]+@[a-z0-9._]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE, message = "Email must be in a valid format") String email,
			Reservation reservation) {

		this.userLoginId = userLoginId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.email = email;
		this.reservation = reservation;
	}

	public User() {

	}

	@Override
	public String toString() {
		return "User [userLoginId=" + userLoginId + ", userName=" + userName + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", contact=" + contact + ", email=" + email + ", reservation="
				+ reservation + "]";
	}
	
	

}
