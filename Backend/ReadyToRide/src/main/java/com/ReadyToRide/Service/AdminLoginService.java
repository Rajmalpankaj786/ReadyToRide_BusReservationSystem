package com.ReadyToRide.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReadyToRide.Exception.LoginException;
import com.ReadyToRide.Model.Admin;
import com.ReadyToRide.Model.AdminDto;
import com.ReadyToRide.Model.CUSession;
import com.ReadyToRide.Repository.CurrentUserSessionRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminLoginService {
	@Autowired
	private CurrentUserSessionRepository sRepo;
	
	
	public CUSession logIntoAccount(AdminDto dto) throws LoginException {
		// TODO Auto-generated method stub
		Admin adm=new Admin();
		if(!adm.username.equalsIgnoreCase(dto.getEmail())) {
			throw new LoginException("Please Enter a valid Username");
		}
		Optional<CUSession> validUserSessionOpt =sRepo.findById(adm.id);
		if(validUserSessionOpt.isPresent()) {
			throw new LoginException("Admin already Logged in with this Username");
		}
		if(adm.password.equals(dto.getPassword())) {
			String key=RandomString.make(6);
			CUSession currentUserSession=new CUSession(adm.id,"admin",key,LocalDateTime.now());
			sRepo.save(currentUserSession);
			return currentUserSession;
		}else {
			throw new LoginException("Please Enter a valid Password");
		}
	}

	
	public String logOutFromAccount(String key) throws LoginException {
		CUSession validUserSession=sRepo.findByUuid(key);
		
		if(validUserSession==null) {
			throw new LoginException("Admin not logged in with this Username.");
		}
		sRepo.delete(validUserSession);
		return "Logged out successfully";
	}
}
