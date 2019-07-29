package com.ningxun.sign.vo;

import java.util.Date;



/**
 * Sign entity. @author MyEclipse Persistence Tools
 */

public class Sign implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uid;
	private Integer tid;
	private Integer raceortrain;
	private Integer raceortrainId;
	private Integer attendanceType;
	private Integer isAttendance;
	private Integer signUp;
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;

	// Constructors

	/** default constructor */
	public Sign() {
	}

	/** full constructor */
	public Sign(Integer id,Integer uid, Integer tid,Integer raceortrain, Integer raceortrainId,
			Integer attendanceType, Integer isAttendance, Integer signUp,
			Date createTime, Integer createUser, Date modifyTime,
			Integer modifyUser, Date deleteTime, Integer deleteUser,
			Integer delState) {
		this.id = id;
		this.uid = uid;
		this.tid =tid;
		this.raceortrain = raceortrain;
		this.raceortrainId = raceortrainId;
		this.attendanceType = attendanceType;
		this.isAttendance = isAttendance;
		this.signUp = signUp;
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

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getRaceortrain() {
		return this.raceortrain;
	}

	public void setRaceortrain(Integer raceortrain) {
		this.raceortrain = raceortrain;
	}

	public Integer getRaceortrainId() {
		return this.raceortrainId;
	}

	public void setRaceortrainId(Integer raceortrainId) {
		this.raceortrainId = raceortrainId;
	}

	public Integer getAttendanceType() {
		return this.attendanceType;
	}

	public void setAttendanceType(Integer attendanceType) {
		this.attendanceType = attendanceType;
	}

	public Integer getIsAttendance() {
		return this.isAttendance;
	}

	public void setIsAttendance(Integer isAttendance) {
		this.isAttendance = isAttendance;
	}

	public Integer getSignUp() {
		return this.signUp;
	}

	public void setSignUp(Integer signUp) {
		this.signUp = signUp;
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

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

}