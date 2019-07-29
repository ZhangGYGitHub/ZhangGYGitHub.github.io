package com.ningxun.score.vo;

import java.util.Date;

/**
 * Racescore entity. @author MyEclipse Persistence Tools
 */

public class Racescore implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer raceId;
	private Integer tid;
	private Integer uid;
	private Integer turnNumber;
	private Integer yellowCard;
	private Integer redCard;
	private Integer getScore;
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;

	// Constructors

	/** default constructor */
	public Racescore() {
	}

	/** full constructor */
	public Racescore(Integer raceId, Integer tid, Integer uid,
			Integer turnNumber, Integer yellowCard, Integer redCard,
			Integer getScore, Date createTime, Integer createUser,
			Date modifyTime, Integer modifyUser, Date deleteTime,
			Integer deleteUser, Integer delState) {
		this.raceId = raceId;
		this.tid = tid;
		this.uid = uid;
		this.turnNumber = turnNumber;
		this.yellowCard = yellowCard;
		this.redCard = redCard;
		this.getScore = getScore;
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

	public Integer getRaceId() {
		return this.raceId;
	}

	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getTurnNumber() {
		return this.turnNumber;
	}

	public void setTurnNumber(Integer turnNumber) {
		this.turnNumber = turnNumber;
	}

	public Integer getYellowCard() {
		return this.yellowCard;
	}

	public void setYellowCard(Integer yellowCard) {
		this.yellowCard = yellowCard;
	}

	public Integer getRedCard() {
		return this.redCard;
	}

	public void setRedCard(Integer redCard) {
		this.redCard = redCard;
	}

	public Integer getGetScore() {
		return this.getScore;
	}

	public void setGetScore(Integer getScore) {
		this.getScore = getScore;
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