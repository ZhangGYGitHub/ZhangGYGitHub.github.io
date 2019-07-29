package com.ningxun.league.vo;

import java.util.Date;

/**
 * LeagueSoccer entity. @author MyEclipse Persistence Tools
 */

public class LeagueSoccer implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lid;
	private Integer sid;
	private Integer num;
	private Integer creator;
	private Date createTime;
	private Integer modifier;
	private Date modifyTime;
	private Integer deletor;
	private Date delTime;
	private Integer delState;

	// Constructors

	/** default constructor */
	public LeagueSoccer() {
	}

	/** minimal constructor */
	public LeagueSoccer(Integer lid, Integer sid, Integer creator,
			Date createTime, Integer delState) {
		this.lid = lid;
		this.sid = sid;
		this.creator = creator;
		this.createTime = createTime;
		this.delState = delState;
	}

	/** full constructor */
	public LeagueSoccer(Integer lid, Integer sid, Integer num, Integer creator,
			Date createTime, Integer modifier, Date modifyTime,
			Integer deletor, Date delTime, Integer delState) {
		this.lid = lid;
		this.sid = sid;
		this.num = num;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
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

	public Integer getLid() {
		return this.lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getCreator() {
		return this.creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getModifier() {
		return this.modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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