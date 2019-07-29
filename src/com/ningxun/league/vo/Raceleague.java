package com.ningxun.league.vo;

import java.util.Date;

/**
 * Raceleague entity. @author MyEclipse Persistence Tools
 */

public class Raceleague implements java.io.Serializable {

	// Fields

	private Integer id;
	private String leagueName;
	private Integer isOpen;
	private String description;
	private Integer teamNum;
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;

	// Constructors

	/** default constructor */
	public Raceleague() {
	}

	/** full constructor */
	public Raceleague(String leagueName, Integer isOpen, String description,
			Integer teamNum, Date createTime, Integer createUser,
			Date modifyTime, Integer modifyUser, Date deleteTime,
			Integer deleteUser, Integer delState) {
		this.leagueName = leagueName;
		this.isOpen = isOpen;
		this.description = description;
		this.teamNum = teamNum;
		this.createTime = createTime;
		this.createUser = createUser;
		this.modifyTime = modifyTime;
		this.modifyUser = modifyUser;
		this.deleteTime = deleteTime;
		this.deleteUser = deleteUser;
		this.delState = delState;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLeagueName() {
		return this.leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public Integer getIsOpen() {
		return this.isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTeamNum() {
		return this.teamNum;
	}

	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(Integer modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Integer getDeleteUser() {
		return this.deleteUser;
	}

	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Integer getDelState() {
		return this.delState;
	}

	public void setDelState(Integer delState) {
		this.delState = delState;
	}

}