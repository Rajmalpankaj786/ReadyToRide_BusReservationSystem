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
import com.ReadyToRide.Model.AdminDto;
import com.ReadyToRide.Model.CUSession;
import com.ReadyToRide.Service.AdminLoginService;



@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminLoginController {
	
	@Autowired
	private AdminLoginService lService;
	
	@PostMapping("/login")
	public ResponseEntity<CUSession> adminLoginHandler(@RequestBody AdminDto dto) throws LoginException{
		return new ResponseEntity<CUSession>(lService.logIntoAccount(dto),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> adminLogoutHandler(@RequestParam String key) throws LoginException{
		String msg=lService.logOutFromAccount(key);
		return new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
	}
}
