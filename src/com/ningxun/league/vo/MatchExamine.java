package com.ningxun.league.vo;

import java.util.Date;

/**
 * 
 * @author MyEclipse Persistence Tools
 */

public class MatchExamine implements java.io.Serializable {



	private Integer id;
	private Integer tid;
	private Integer lmid;
	private Integer sid;
	private Integer num;
	private Integer submitter;
	private Date subTime;
	private Integer auditor;
	private Date autTime;
	private Integer result;

	// Constructors

	/** default constructor */
	public MatchExamine() {
	}

	/** minimal constructor */
	public MatchExamine(Integer submitter, Date subTime) {
		this.submitter = submitter;
		this.subTime = subTime;
	}

	/** full constructor */
	public MatchExamine(Integer tid, Integer lmid, Integer sid, Integer num,
			Integer submitter, Date subTime, Integer auditor,
			Date autTime, Integer result) {
		this.tid = tid;
		this.lmid = lmid;
		this.sid = sid;
		this.num = num;
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

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
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

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
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