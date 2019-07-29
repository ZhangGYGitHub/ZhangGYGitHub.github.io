package com.ningxun.league.vo;

import java.util.Date;

/**
 * 2018-3-16
 * @author liudongxin
 */

public class Referee{

	public static final Integer JOIN_SUCCESS = 1;  //已同意邀请并加入裁判组
	public static final Integer JOIN_FAIL = 0;     //已同意邀请但未加入裁判组
	
	private Integer id;
	private Integer lid;
	private Integer uid;
	private Integer join;
	private Date createTime;
	private Integer creator;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;

	// Constructors

	/** default constructor */
	public Referee() {
	}

	/** minimal constructor */
	public Referee(Integer lid) {
		this.lid = lid;
	}

	/** full constructor */
	public Referee(Integer lid, Integer uid, Integer join,
			Date createTime, Integer creator, Date modifyTime,
			Integer modifyUser, Date deleteTime, Integer deleteUser,
			Integer delState) {
		this.lid = lid;
		this.uid = uid;
		this.join = join;
		this.createTime = createTime;
		this.creator = creator;
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

	public Integer getLid() {
		return this.lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getJoin() {
		return this.join;
	}

	public void setJoin(Integer join) {
		this.join = join;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreator() {
		return this.creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
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