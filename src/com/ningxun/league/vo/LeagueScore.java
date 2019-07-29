package com.ningxun.league.vo;

import java.util.Date;

/**
 * LeagueScore entity. @author MyEclipse Persistence Tools
 */

public class LeagueScore implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lid;
	private Integer team;
	private Integer win;
	private Integer lose;
	private Integer draw;
	private Integer inNum;
	private Integer outNum;
	private Integer gd;
	private Integer score;
	private Integer creator;
	private Date createTime;
	private Integer modifier;
	private Date modifyTime;
	private Integer deletor;
	private Date delTime;
	private Integer delState;

	// Constructors

	/** default constructor */
	public LeagueScore() {
	}

	/** minimal constructor */
	public LeagueScore(Integer lid, Integer team, Integer creator,
			Date createTime, Integer delState) {
		this.lid = lid;
		this.team = team;
		this.creator = creator;
		this.createTime = createTime;
		this.delState = delState;
	}

	/** full constructor */
	public LeagueScore(Integer lid, Integer team, Integer win, Integer lose,
			Integer draw, Integer inNum, Integer outNum, Integer gd,
			Integer score, Integer creator, Date createTime,
			Integer modifier, Date modifyTime, Integer deletor,
			Date delTime, Integer delState) {
		this.lid = lid;
		this.team = team;
		this.win = win;
		this.lose = lose;
		this.draw = draw;
		this.inNum = inNum;
		this.outNum = outNum;
		this.gd = gd;
		this.score = score;
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

	public Integer getTeam() {
		return this.team;
	}

	public void setTeam(Integer team) {
		this.team = team;
	}

	public Integer getWin() {
		return this.win;
	}

	public void setWin(Integer win) {
		this.win = win;
	}

	public Integer getLose() {
		return this.lose;
	}

	public void setLose(Integer lose) {
		this.lose = lose;
	}

	public Integer getDraw() {
		return this.draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getInNum() {
		return this.inNum;
	}

	public void setInNum(Integer inNum) {
		this.inNum = inNum;
	}

	public Integer getOutNum() {
		return this.outNum;
	}

	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}

	public Integer getGd() {
		return this.gd;
	}

	public void setGd(Integer gd) {
		this.gd = gd;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
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