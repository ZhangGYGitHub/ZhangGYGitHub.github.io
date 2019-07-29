package com.ningxun.league.vo;

/**
 * 2018-3-16
 * @author 
 */

public class TeamLeague implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer tid;
	private Integer lid;

	// Constructors

	/** default constructor */
	public TeamLeague() {
	}

	/** full constructor */
	public TeamLeague(Integer tid, Integer lid) {
		this.tid = tid;
		this.lid = lid;
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

	public Integer getLid() {
		return this.lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

}