package com.ningxun.goods.vo;

/**
 * TeamGoods entity. @author MyEclipse Persistence Tools
 */

public class TeamGoods implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer tid;
	private Integer gid;

	// Constructors

	/** default constructor */
	public TeamGoods() {
	}

	/** full constructor */
	public TeamGoods(Integer tid, Integer gid) {
		this.tid = tid;
		this.gid = gid;
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

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

}