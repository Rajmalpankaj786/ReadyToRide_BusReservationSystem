package com.ReadyToRide.Model;

import jakarta.validation.constraints.NotNull;

public class AdminDto {
	@NotNull(message ="Username cannot be null.")
	private String username;
	@NotNull(message ="Password cannot be null.")
	private String password;
	@Override
	public String toString() {
		return "AdminDto [username=" + username + ", password=" + password + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AdminDto(@NotNull(message = "Username cannot be null.") String username,
			@NotNull(message = "Password cannot be null.") String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public AdminDto() {
		super();
	}
	
	
}
