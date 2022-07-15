package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "shedlock")
public class Shedlock implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "name", length = 64)
	private String name;

	@Column(name = "lock_until")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:fff")
	private Date lock_until;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:fff")
	@Column(name = "locked_at")
	private Date locked_at;

	@Column(name = "locked_by", length = 255)
	private String locked_by;

	
	public Shedlock() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLock_until() {
		return lock_until;
	}

	public void setLock_until(Date lock_until) {
		this.lock_until = lock_until;
	}

	public Date getLocked_at() {
		return locked_at;
	}

	public void setLocked_at(Date locked_at) {
		this.locked_at = locked_at;
	}

	public String getLocked_by() {
		return locked_by;
	}

	public void setLocked_by(String locked_by) {
		this.locked_by = locked_by;
	}

	
	
	
	
}
