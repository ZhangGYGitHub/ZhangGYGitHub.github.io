package com.ningxun.league.vo;

import java.util.Date;

/**
 * LeagueMatch entity. @author MyEclipse Persistence Tools
 */

public class LeagueMatch implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer teamOne;
	private Integer teamTwo;
	private Integer leagueId;
	private Integer site;
	private Integer turn;
	private Integer oneWin;
	private Integer twoWin;
	private Date startTime;
	private Date endTime;
	private Integer finish;
	private Integer creator;
	private Date createTime;
	private Integer modifier;
	private Date modifyTime;
	private Integer deletor;
	private Date delTime;
	private Integer delState;

	// Constructors

	/** default constructor */
	public LeagueMatch() {
	}

	/** minimal constructor */
	public LeagueMatch(Integer leagueId, Integer site, Integer turn,
			Integer creator, Date createTime, Integer delState) {
		this.leagueId = leagueId;
		this.site = site;
		this.turn = turn;
		this.creator = creator;
		this.createTime = createTime;
		this.delState = delState;
	}

	/** full constructor */
	public LeagueMatch(Integer teamOne, Integer teamTwo, Integer leagueId,
			Integer site, Integer turn, Integer oneWin, Integer twoWin,
			Date startTime, Date endTime, Integer finish,
			Integer creator, Date createTime, Integer modifier,
			Date modifyTime, Integer deletor, Date delTime,
			Integer delState) {
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
		this.leagueId = leagueId;
		this.site = site;
		this.turn = turn;
		this.oneWin = oneWin;
		this.twoWin = twoWin;
		this.startTime = startTime;
		this.endTime = endTime;
		this.finish = finish;
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

	public Integer getTeamOne() {
		return this.teamOne;
	}

	public void setTeamOne(Integer teamOne) {
		this.teamOne = teamOne;
	}

	public Integer getTeamTwo() {
		return this.teamTwo;
	}

	public void setTeamTwo(Integer teamTwo) {
		this.teamTwo = teamTwo;
	}

	public Integer getLeagueId() {
		return this.leagueId;
	}

	public void setLeagueId(Integer leagueId) {
		this.leagueId = leagueId;
	}

	public Integer getSite() {
		return this.site;
	}

	public void setSite(Integer site) {
		this.site = site;
	}

	public Integer getTurn() {
		return this.turn;
	}

	public void setTurn(Integer turn) {
		this.turn = turn;
	}

	public Integer getOneWin() {
		return this.oneWin;
	}

	public void setOneWin(Integer oneWin) {
		this.oneWin = oneWin;
	}

	public Integer getTwoWin() {
		return this.twoWin;
	}

	public void setTwoWin(Integer twoWin) {
		this.twoWin = twoWin;
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

	public Integer getFinish() {
		return this.finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
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