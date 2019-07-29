package com.ningxun.league.vo;

import java.util.Date;

/**
 * LeagueCard entity. @author MyEclipse Persistence Tools
 */

public class LeagueCard implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lid;
	private Integer lmid;
	private Integer sid;
	private Integer yellowNum;
	private Integer redNum;
	private Integer submitter;
	private Date subTime;
	private Integer auditor;
	private Date autTime;
	private Integer result;

	// Constructors

	/** default constructor */
	public LeagueCard() {
	}

	/** full constructor */
	public LeagueCard(Integer lid, Integer lmid, Integer sid,
			Integer yellowNum, Integer redNum, Integer submitter, Date subTime,
			Integer auditor, Date autTime, Integer result) {
		this.lid = lid;
		this.lmid = lmid;
		this.sid = sid;
		this.yellowNum = yellowNum;
		this.redNum = redNum;
		this.submitter = submitter;
		this.subTime = subTime;
		this.auditor = auditor;
		this.autTime = autTime;
		this.result = result;
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

	public Integer getLmid() {
		return this.lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}

	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getYellowNum() {
		return this.yellowNum;
	}

	public void setYellowNum(Integer yellowNum) {
		this.yellowNum = yellowNum;
	}

	public Integer getRedNum() {
		return this.redNum;
	}

	public void setRedNum(Integer redNum) {
		this.redNum = redNum;
	}

	public Integer getSubmitter() {
		return this.submitter;
	}

	public void setSubmitter(Integer submitter) {
		this.submitter = submitter;
	}

	public Date getSubTime() {
		return this.subTime;
	}

	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}

	public Integer getAuditor() {
		return this.auditor;
	}

	public void setAuditor(Integer auditor) {
		this.auditor = auditor;
	}

	public Date getAutTime() {
		return this.autTime;
	}

	public void setAutTime(Date autTime) {
		this.autTime = autTime;
	}

	public Integer getResult() {
		return this.result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

}