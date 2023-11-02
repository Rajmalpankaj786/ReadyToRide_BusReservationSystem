package com.ReadyToRide.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ReadyToRide.Exception.UserException;
import com.ReadyToRide.Model.User;
import com.ReadyToRide.Service.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping
public class UserController {
 
	@Autowired
	private UserService uService;
	
	@PostMapping(value = "/users")
	public ResponseEntity<User> addUser(@RequestBody User user) throws UserException {
		return new ResponseEntity<User>(uService.addUser(user),HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<User> updateUserHandler(@RequestBody User user,@RequestParam String key) throws UserException {
		User u=uService.updateUser(user,key);
		return new ResponseEntity<User>(u,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("delete/{userId}")
	public ResponseEntity<User> deleteUserHandler(@PathVariable("userId") Integer userId,@RequestParam String key) throws UserException {
		User u=uService.deleteUser(userId, key);
		return new ResponseEntity<User>(u,HttpStatus.ACCEPTED);
	}
	@GetMapping("/view/{userId}/{key}")
	public ResponseEntity<User> viewUserHandler(@PathVariable("userId") Integer userId,@RequestParam String key) throws UserException {
		User u=uService.viewUser(userId,key);
		return new ResponseEntity<User>(u,HttpStatus.FOUND);
	}
	
	@GetMapping("/viewall")
	public ResponseEntity<List<User>> viewAllUsersHandler(@RequestParam String key) throws UserException {
		List<User> users=uService.viewAllUsers(key);
		return new ResponseEntity<List<User>>(users,HttpStatus.FOUND);
	}
}
