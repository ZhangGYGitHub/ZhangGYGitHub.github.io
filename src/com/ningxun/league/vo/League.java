package com.ningxun.league.vo;

import java.util.Date;

/**2018-3-21
 *  @
 */

public class League implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer scoreType;
	private Integer winScore;
	private Integer loseScore;
	private Integer drawScore;
	private String attachmentOldName;
	private String attachmentNewName;
	private Integer teamNum;
	private String introduction;
	private Integer creatorId;
	private Date createTime;
	private Integer modifier;
	private Date modifyTime;
	private Integer deletor;
	private Date delTime;
	private Integer delState;

	// Constructors

	/** default constructor */
	public League() {
	}

	/** minimal constructor */
	public League(String name, Integer scoreType, Integer teamNum,
			Integer creatorId, Date createTime, Integer delState) {
		this.name = name;
		this.scoreType = scoreType;
		this.teamNum = teamNum;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.delState = delState;
	}

	/** full constructor */
	public League(String name, Integer scoreType, Integer winScore,
			Integer loseScore, Integer drawScore, String attachmentOldName,
			String attachmentNewName, Integer teamNum, String introduction,
			Integer creatorId, Date createTime, Integer modifier,
			Date modifyTime, Integer deletor, Date delTime,
			Integer delState) {
		this.name = name;
		this.scoreType = scoreType;
		this.winScore = winScore;
		this.loseScore = loseScore;
		this.drawScore = drawScore;
		this.attachmentOldName = attachmentOldName;
		this.attachmentNewName = attachmentNewName;
		this.teamNum = teamNum;
		this.introduction = introduction;
		this.creatorId = creatorId;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScoreType() {
		return this.scoreType;
	}

	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
	}

	public Integer getWinScore() {
		return this.winScore;
	}

	public void setWinScore(Integer winScore) {
		this.winScore = winScore;
	}

	public Integer getLoseScore() {
		return this.loseScore;
	}

	public void setLoseScore(Integer loseScore) {
		this.loseScore = loseScore;
	}

	public Integer getDrawScore() {
		return this.drawScore;
	}

	public void setDrawScore(Integer drawScore) {
		this.drawScore = drawScore;
	}

	public String getAttachmentOldName() {
		return this.attachmentOldName;
	}

	public void setAttachmentOldName(String attachmentOldName) {
		this.attachmentOldName = attachmentOldName;
	}

	public String getAttachmentNewName() {
		return this.attachmentNewName;
	}

	public void setAttachmentNewName(String attachmentNewName) {
		this.attachmentNewName = attachmentNewName;
	}

	public Integer getTeamNum() {
		return this.teamNum;
	}

	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
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