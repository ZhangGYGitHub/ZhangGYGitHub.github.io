package com.ningxun.race.vo;


import java.util.Date;

/**
 * Race entity. @author MyEclipse Persistence Tools
 */

public class Race implements java.io.Serializable {

	// Fields

	private Integer id;
	private String raceName;
	private Integer type;
	private Integer leagueId;
	private Date startTime;
	private Date endTime;
	private Integer signType;
	private Integer signTime;
	private String place;
	private Integer oppoId;
	private String opponent;
	private String waste;
	private String remark;
	private Integer opScore;
	private Integer myScore;
	private String clothescolour;
	private String league;
	private Integer isRelease;
	private Integer tid;
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;

	// Constructors

	/** default constructor */
	public Race() {
	}

	/** full constructor */
	public Race(String raceName, Integer type, Integer leagueId, Date startTime,
			Date endTime, Integer signType, Integer signTime,
			String place, Integer oppoId, String opponent, String waste,
			String remark, Integer opScore, Integer myScore,
			String clothescolour, String league, Integer isRelease,
			Integer tid, Date createTime, Integer createUser,
			Date modifyTime, Integer modifyUser, Date deleteTime,
			Integer deleteUser, Integer delState) {
		this.raceName = raceName;
		this.type = type;
		this.leagueId = leagueId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.signType = signType;
		this.signTime = signTime;
		this.place = place;
		this.oppoId = oppoId;
		this.opponent = opponent;
		this.waste = waste;
		this.remark = remark;
		this.opScore = opScore;
		this.myScore = myScore;
		this.clothescolour = clothescolour;
		this.league = league;
		this.isRelease = isRelease;
		this.tid = tid;
		this.createTime = createTime;
		this.createUser = createUser;
		this.modifyTime = modifyTime;
		this.modifyUser = modifyUser;
		this.deleteTime = deleteTime;
		this.deleteUser = deleteUser;
		this.delState = delState;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRaceName() {
		return this.raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getSignType() {
		return this.signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Integer getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Integer signTime) {
		this.signTime = signTime;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getOppoId() {
		return this.oppoId;
	}

	public void setOppoId(Integer oppoId) {
		this.oppoId = oppoId;
	}

	public String getOpponent() {
		return this.opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public String getWaste() {
		return this.waste;
	}

	public void setWaste(String waste) {
		this.waste = waste;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOpScore() {
		return this.opScore;
	}

	public void setOpScore(Integer opScore) {
		this.opScore = opScore;
	}

	public Integer getMyScore() {
		return this.myScore;
	}

	public void setMyScore(Integer myScore) {
		this.myScore = myScore;
	}

	public String getClothescolour() {
		return this.clothescolour;
	}

	public void setClothescolour(String clothescolour) {
		this.clothescolour = clothescolour;
	}

	public String getLeague() {
		return this.league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public Integer getIsRelease() {
		return this.isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(Integer modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Integer getDeleteUser() {
		return this.deleteUser;
	}

	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Integer getDelState() {
		return this.delState;
	}

	public void setDelState(Integer delState) {
		this.delState = delState;
	}

	public Integer getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Integer leagueId) {
		this.leagueId = leagueId;
	}

}