package com.ningxun.feedback.vo;

import java.util.Date;

/**
 * LeagueComment entity. @author MyEclipse Persistence Tools
 */

public class LeagueComment implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer matchId;
	private Integer commentator;
	private String content;
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
	public LeagueComment() {
	}

	/** minimal constructor */
	public LeagueComment(Integer delState) {
		this.delState = delState;
	}

	/** full constructor */
	public LeagueComment(Integer matchId, Integer commentator, String content,
			Integer score, Integer creator, Date createTime,
			Integer modifier, Date modifyTime, Integer deletor,
			Date delTime, Integer delState) {
		this.matchId = matchId;
		this.commentator = commentator;
		this.content = content;
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

	public Integer getMatchId() {
		return this.matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getCommentator() {
		return this.commentator;
	}

	public void setCommentator(Integer commentator) {
		this.commentator = commentator;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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