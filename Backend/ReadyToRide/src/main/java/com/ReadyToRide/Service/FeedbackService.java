package com.ReadyToRide.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReadyToRide.Exception.BusException;
import com.ReadyToRide.Exception.FeedbackException;
import com.ReadyToRide.Exception.UserException;
import com.ReadyToRide.Model.Bus;
import com.ReadyToRide.Model.CUSession;
import com.ReadyToRide.Model.Feedback;
import com.ReadyToRide.Model.User;
import com.ReadyToRide.Repository.BusRepository;
import com.ReadyToRide.Repository.CurrentUserSessionRepository;
import com.ReadyToRide.Repository.FeedbackRepository;
import com.ReadyToRide.Repository.UserRepository;



@Service
public class FeedbackService{

	@Autowired
	private FeedbackRepository fdao;
	
	@Autowired
	private UserRepository udao;
	
	@Autowired
	private BusRepository bdao;
	
	@Autowired
	private CurrentUserSessionRepository srepo;
	

	public String addFeedback(Integer busId, Feedback feedback, String key) throws FeedbackException, UserException, BusException {
	    CUSession loggedInUser = srepo.findByUuid(key);
	    
	    if (loggedInUser == null) {
	        throw new UserException("Please provide a valid key to add Feedback");
	    }
	    
	    User user = udao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User with Id " + loggedInUser.getUserId() + " not found"));
	    
	    if (user.getUserLoginId() == loggedInUser.getUserId()) {
	        Bus b = bdao.findById(busId).orElseThrow(() -> new BusException("Bus with Id " + busId + " not found"));
	        
	        // Ensure the feedback object is not null
	        if (feedback == null) {
	            throw new FeedbackException("Feedback is null. Please provide valid feedback data.");
	        }
	        
	        feedback.setBus(b);
	        feedback.setUser(user);
	        
	        // Save the feedback and check if it's successfully saved
	        Feedback savedFeedback = fdao.save(feedback);
	        if (savedFeedback == null) {
	            throw new FeedbackException("Failed to save feedback.");
	        }
	        
	        return "Feedback Added Successfully: " + savedFeedback;
	    } else {
	        throw new UserException("Invalid User Id");
	    }
	}

public Feedback updateFeedback( Feedback feedback,String key) throws FeedbackException, UserException {
		
		CUSession loggedInUser=srepo.findByUuid(key);
		if(loggedInUser==null) {
			throw new UserException("Please provide a valid key to update user");
		}
		User user = udao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User with Id " + loggedInUser.getUserId() + " not found"));
		if(user.getUserLoginId()==loggedInUser.getUserId()) {
			Feedback f = fdao.findById(feedback.getFeedbackId()).orElseThrow(() -> new FeedbackException("Feedback with Id " + feedback.getFeedbackId() + " does not exist"));
			
			if (feedback.getComments() != null) f.setComments(feedback.getComments());
			if (feedback.getDriverRating() != null) f.setDriverRating(feedback.getDriverRating());
			if (feedback.getServiceRating() != null) f.setServiceRating(feedback.getServiceRating());
			if (feedback.getOverallRating() != null) f.setOverallRating(feedback.getOverallRating());
			
			Feedback updated = fdao.save(f);
			
			return updated;
		}else throw new UserException("Invalid User Id");
		
	}


	public Feedback viewFeedback(Integer feedbackId,String key) throws FeedbackException, UserException {
		CUSession loggedInUser=srepo.findByUuid(key);
		if(loggedInUser==null) {
			throw new UserException("Please provide a valid key to update user");
		}
		User user = udao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User with Id " + loggedInUser.getUserId() + " not found"));
		if(user.getUserLoginId()==loggedInUser.getUserId()) {
			Feedback f = fdao.findById(feedbackId).orElseThrow(() -> new FeedbackException("Feedback with Id " + feedbackId + " does not exist"));
			return f;
		}else throw new UserException("Invalid User Id");
		
	}

	
	public List<Feedback> viewAllFeedback(String key) throws FeedbackException, UserException {
		CUSession loggedInUser=srepo.findByUuid(key);
		if(loggedInUser==null) {
			throw new UserException("Please provide a valid key to update user");
		}
		User user = udao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User with Id " + loggedInUser.getUserId() + " not found"));
		if(user.getUserLoginId()==loggedInUser.getUserId()) {
			List<Feedback> f= fdao.findAll();
			
			if (!f.isEmpty()) return f;
			else throw new FeedbackException("Feedback not found");
		}else throw new UserException("Invalid User Id");
		
	}
}
