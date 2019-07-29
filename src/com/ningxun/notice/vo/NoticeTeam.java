package com.ningxun.notice.vo;

/**
 * NoticeTeam entity. @author MyEclipse Persistence Tools
 */

public class NoticeTeam implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer nid;
	private Integer tid;

	// Constructors

	/** default constructor */
	public NoticeTeam() {
	}

	/** full constructor */
	public NoticeTeam(Integer nid, Integer tid) {
		this.nid = nid;
		this.tid = tid;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNid() {
		return this.nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

}