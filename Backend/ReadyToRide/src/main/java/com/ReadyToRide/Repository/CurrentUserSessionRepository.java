package com.ReadyToRide.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ReadyToRide.Model.CUSession;

public interface CurrentUserSessionRepository extends JpaRepository<CUSession, Integer>{
	public CUSession findByUuid(String uuid);
}
