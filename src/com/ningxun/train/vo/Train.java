package com.ningxun.train.vo;

import java.util.Date;

import com.ningxun.tools.FormatUtil;



/**
 * Train entity. @author MyEclipse Persistence Tools
 */

public class Train implements java.io.Serializable {

	// Fields

	private Integer id;
	private String trainName;
	private String trainContent;
	private Integer issign;
	private Integer signTime;
	private String clothescolour;
	private String trainPlace;
	private Date startTime;
	private Date endTime;
	private Integer isRelease;
	private Integer tid;
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;

	// Constructors

	/** default constructor */
	public Train() {
	}

	/** minimal constructor */
	public Train(Integer delState) {
		this.delState = delState;
	}

	/** full constructor */
	public Train(String trainName, String trainContent, Integer issign,
			Integer signTime, String clothescolour, String trainPlace,
			Date startTime, Date endTime, Integer isRelease,
			Integer tid, Date createTime, Integer createUser,
			Date modifyTime, Integer modifyUser, Date deleteTime,
			Integer deleteUser, Integer delState) {
		this.trainName = trainName;
		this.trainContent = trainContent;
		this.issign = issign;
		this.signTime = signTime;
		this.clothescolour = clothescolour;
		this.trainPlace = trainPlace;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isRelease = isRelease;
		this.tid = tid;
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

	public String getTrainName() {
		return this.trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainContent() {
		return this.trainContent;
	}

	public void setTrainContent(String trainContent) {
		this.trainContent = trainContent;
	}

	public Integer getIssign() {
		return this.issign;
	}

	public void setIssign(Integer issign) {
		this.issign = issign;
	}

	public Integer getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Integer signTime) {
		this.signTime = signTime;
	}

	public String getClothescolour() {
		return this.clothescolour;
	}

	public void setClothescolour(String clothescolour) {
		this.clothescolour = clothescolour;
	}

	public String getTrainPlace() {
		return this.trainPlace;
	}

	public void setTrainPlace(String trainPlace) {
		this.trainPlace = trainPlace;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getIsRelease() {
		return this.isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
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