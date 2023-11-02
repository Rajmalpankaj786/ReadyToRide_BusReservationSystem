package com.ReadyToRide.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CUSession {
	@Id
	@Column(unique = true)
	private Integer userId;
	private String name;
	private String uuid;
	private LocalDateTime localDateTime;

	public Integer getUserId() {
		return userId;
	}

	public String getType() {
		return name;
	}

	public void setType(String type) {
		this.name = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public CUSession(Integer userId, String type, String uuid, LocalDateTime localDateTime) {
		super();
		this.userId = userId;
		this.name = type;
		this.uuid = uuid;
		this.localDateTime = localDateTime;
	}

	public CUSession() {
		super();
	}

	@Override
	public String toString() {
		return "CurrentUserSession [userId=" + userId + ", type=" + name + ", uuid=" + uuid + ", localDateTime="
				+ localDateTime + "]";
	}

}
