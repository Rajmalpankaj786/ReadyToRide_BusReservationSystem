package com.ReadyToRide.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErrorDetails> userExceptionHandler(UserException ue,WebRequest req){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(ue.getMessage());
		err.setDetails(req.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BusException.class)
	public ResponseEntity<MyErrorDetails> myBusException(BusException be, WebRequest webReq){
		
		MyErrorDetails red = new MyErrorDetails();
		red.setTimestamp(LocalDateTime.now());
		red.setMessage(be.getMessage());
		red.setDetails(webReq.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(red, HttpStatus.BAD_REQUEST);
	}
	
}
