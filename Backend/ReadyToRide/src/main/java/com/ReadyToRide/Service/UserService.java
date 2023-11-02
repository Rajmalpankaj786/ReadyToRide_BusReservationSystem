package com.ReadyToRide.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReadyToRide.Exception.UserException;
import com.ReadyToRide.Model.CUSession;
import com.ReadyToRide.Model.User;
import com.ReadyToRide.Repository.CurrentUserSessionRepository;
import com.ReadyToRide.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CurrentUserSessionRepository currentUserSessionRepository;

	public User addUser(User user) throws UserException {
		User us = userRepository.findByEmail(user.getEmail());

		if (us != null) {
			throw new UserException("User already exist with this Email Id : " + user.getEmail());
		}
		return userRepository.save(user);
	}

	public User updateUser(User user, String key) throws UserException {
		CUSession loggedInUser=currentUserSessionRepository.findByUuid(key);
		if(loggedInUser==null) {
			throw new UserException("Please provide a valid key to update user");
		}
		User curr=userRepository.findById(user.getUserLoginId())
				.orElseThrow(()-> new UserException("User with User Id "+user.getUserLoginId()+" does not exist"));
		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {
			if (user.getContact() != null) curr.setContact(user.getContact());
			if (user.getEmail() != null) curr.setEmail(user.getEmail());
			if (user.getFirstName() != null) curr.setFirstName(user.getFirstName());
			if (user.getLastName() != null) curr.setLastName(user.getLastName());
			if (user.getPassword() != null) curr.setPassword(user.getPassword());
			
			User saved = userRepository.save(curr);
			return saved;
		}
		if(user.getUserLoginId()==loggedInUser.getUserId()) {
			if (user.getContact() != null) curr.setContact(user.getContact());
			if (user.getEmail() != null) curr.setEmail(user.getEmail());
			if (user.getFirstName() != null) curr.setFirstName(user.getFirstName());
			if (user.getLastName() != null) curr.setLastName(user.getLastName());
			if (user.getPassword() != null) curr.setPassword(user.getPassword());
			
			
			User saved = userRepository.save(curr);
			
			return saved;
			
		}
		else throw new UserException("Access denied.");
	}

	public User deleteUser(Integer userId, String key) throws UserException {
		CUSession loggedInUser=currentUserSessionRepository.findByUuid(key);
		if(loggedInUser==null) {
			throw new UserException("Please provide a valid key to delete user.");
		}
		User u=userRepository.findById(userId)
				.orElseThrow(()-> new UserException("User with User Id "+userId+" does not exist"));
		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {
			userRepository.delete(u);
			return u;
		}
		if(u.getUserLoginId()==loggedInUser.getUserId()) {
			userRepository.delete(u);
			currentUserSessionRepository.delete(loggedInUser);
			return u;
		}
		else {
			throw new UserException("Access denied.");
		}
		
	}

	public User viewUser(Integer userId, String key) throws UserException {
		CUSession loggedInUser = currentUserSessionRepository.findByUuid(key);
		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to view user.");
		}
		if (loggedInUser.getType().equalsIgnoreCase("Admin")) {

			User u = userRepository.findById(userId)
					.orElseThrow(() -> new UserException("User with User Id " + userId + " does not exist"));

			return u;
		} else if (loggedInUser.getType().equalsIgnoreCase("user")) {
			User u = userRepository.findById(userId)
					.orElseThrow(() -> new UserException("User with User Id " + userId + " does not exist"));
			if (u.getUserLoginId() == loggedInUser.getUserId()) {
				return u;
			} else {
				throw new UserException("Invalid User details, please login first");
			}
		} else
			throw new UserException("Access denied");
	}

	public List<User> viewAllUsers(String key) throws UserException {
		CUSession loggedInUser = currentUserSessionRepository.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to view all user.");
		}
		if (loggedInUser.getType().equalsIgnoreCase("admin")) {

			List<User> users = userRepository.findAll();
			if (users.size() != 0) {
				return users;
			} else {
				throw new UserException("No User Found.");
			}
		} else
			throw new UserException("Access denied");
	}

}
