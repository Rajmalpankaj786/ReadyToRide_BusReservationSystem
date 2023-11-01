package com.ReadyToRide.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ReadyToRide.Model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}
