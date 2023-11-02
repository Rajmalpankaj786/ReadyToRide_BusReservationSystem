package com.ReadyToRide.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ReadyToRide.Model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
}
