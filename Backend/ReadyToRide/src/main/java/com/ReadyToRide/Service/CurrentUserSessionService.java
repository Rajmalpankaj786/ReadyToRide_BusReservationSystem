package com.ReadyToRide.Service;

import java.time.LocalDateTime;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReadyToRide.Exception.LoginException;
import com.ReadyToRide.Model.CUSession;
import com.ReadyToRide.Model.LoginDTO;
import com.ReadyToRide.Model.User;
import com.ReadyToRide.Repository.CurrentUserSessionRepository;
import com.ReadyToRide.Repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class CurrentUserSessionService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CurrentUserSessionRepository currentUserSessionRepository;

	public CUSession logIntoAccount(LoginDTO dto) throws LoginException {
		User user = userRepository.findByEmail(dto.getEmail());

		if (user == null) {
			throw new LoginException("Please Enter a valid Email & password.");
		}

		Optional<CUSession> validUserSessionOpt = currentUserSessionRepository.findById(user.getUserLoginId());
		if (validUserSessionOpt.isPresent()) {
			throw new LoginException("User already Logged in with this email.");
		}
		if (user.getPassword().equals(dto.getPassword())) {
			String key = RandomString.make(6);
			String name = user.getFirstName().concat(" " + user.getLastName());
			CUSession currentUserSession = new CUSession(user.getUserLoginId(), name, key,
					LocalDateTime.now());
			currentUserSessionRepository.save(currentUserSession);
			return currentUserSession;
		} else {
			throw new LoginException("Please Enter a valid password");
		}
	}

	public String logOutFromAccount(String key) throws LoginException {

		CUSession validUserSession = currentUserSessionRepository.findByUuid(key);

		if (validUserSession == null) {
			throw new LoginException("User not logged in with this email.");
		}
		currentUserSessionRepository.delete(validUserSession);
		return "Logged out successfully.";
	}
}
