package com.ReadyToRide.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ReadyToRide.Exception.LoginException;
import com.ReadyToRide.Model.CUSession;
import com.ReadyToRide.Model.LoginDTO;
import com.ReadyToRide.Service.CurrentUserSessionService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserLoginController {
	@Autowired
	private CurrentUserSessionService lService;
	
	@PostMapping("/login")
	public ResponseEntity<CUSession> userLoginHandler(@Valid @RequestBody LoginDTO dto) throws LoginException{
//		String msg=lService.logIntoAccount(dto);
		return new ResponseEntity<CUSession>(lService.logIntoAccount(dto),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> userLogoutHandler(@RequestParam String key) throws LoginException{
		String msg=lService.logOutFromAccount(key);
		return new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
	}
}