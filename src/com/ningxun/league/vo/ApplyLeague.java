package com.ningxun.league.vo;

import java.util.Date;

/**
 * ApplyLeague entity. @author MyEclipse Persistence Tools
 */

public class ApplyLeague {

	// Fields

	private Integer id;
	private Integer uid;
	private Integer lid;
	private Integer applyHandle;
	private Date applyTime;
	private Date handleTime;
	private Integer handleUser;
	private String reason;

	// Constructors

	/** default constructor */
	public ApplyLeague() {
	}

	/** full constructor */
	public ApplyLeague(Integer uid, Integer lid, Integer applyHandle,
			Date applyTime, Date handleTime, Integer handleUser,
			String reason) {
		this.uid = uid;
		this.lid = lid;
		this.applyHandle = applyHandle;
		this.applyTime = applyTime;
		this.handleTime = handleTime;
		this.handleUser = handleUser;
		this.reason = reason;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getLid() {
		return this.lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public Integer getApplyHandle() {
		return this.applyHandle;
	}

	public void setApplyHandle(Integer applyHandle) {
		this.applyHandle = applyHandle;
	}

	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getHandleTime() {
		return this.handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Integer getHandleUser() {
		return this.handleUser;
	}

	public void setHandleUser(Integer handleUser) {
		this.handleUser = handleUser;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}