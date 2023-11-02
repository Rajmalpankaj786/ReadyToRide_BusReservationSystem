package com.ReadyToRide.Model;

import jakarta.validation.constraints.NotNull;

public class LoginDTO {
	@NotNull(message ="email cannot be null.")
	private String email;
	@NotNull(message ="Password cannot be null.")
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginDTO(@NotNull(message = "email cannot be null.") String email,
			@NotNull(message = "Password cannot be null.") String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public LoginDTO() {
		super();
	}
	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", password=" + password + "]";
	}
	
	
	
}
