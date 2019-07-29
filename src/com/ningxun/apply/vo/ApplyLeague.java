package com.ningxun.apply.vo;

import java.util.Date;

/**
 * ApplyLeague entity. @author MyEclipse Persistence Tools
 */

public class ApplyLeague implements java.io.Serializable {

	// Fields
	public static final Integer APPLY_HANDLE_WAITHANDLE = 0;
	public static final Integer APPLY_HANDLE_AGREE = 1;
	public static final Integer APPLY_HANDLE_REJECT = 2;
	public static final Integer APPLY_HANDLE_IGNORE = 3;
	private Integer id;
	private Integer uid;
	private Integer lid;
	private Integer applyHandle;
	private Date applyTime;
	private Date handleTime;
	private Integer handleUser;
	private String reason;
	private Date createTime;
	private Integer deletor;
	private Date delTime;
	private Integer delState;

	// Constructors

	/** default constructor */
	public ApplyLeague() {
	}

	/** minimal constructor */
	public ApplyLeague(Integer delState) {
		this.delState = delState;
	}

	/** full constructor */
	public ApplyLeague(Integer uid, Integer lid, Integer applyHandle,
			Date applyTime, Date handleTime, Integer handleUser,
			String reason, Date createTime, Integer deletor,
			Date delTime, Integer delState) {
		this.uid = uid;
		this.lid = lid;
		this.applyHandle = applyHandle;
		this.applyTime = applyTime;
		this.handleTime = handleTime;
		this.handleUser = handleUser;
		this.reason = reason;
		this.createTime = createTime;
		this.deletor = deletor;
		this.delTime = delTime;
		this.delState = delState;
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

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeletor() {
		return this.deletor;
	}

	public void setDeletor(Integer deletor) {
		this.deletor = deletor;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public Integer getDelState() {
		return this.delState;
	}

	public void setDelState(Integer delState) {
		this.delState = delState;
	}

}