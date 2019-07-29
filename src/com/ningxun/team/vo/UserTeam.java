package com.ningxun.team.vo;

/**
 * @author hujian
 * @date 2017年7月20日
 * @version 1.0
 */
public class UserTeam {
	
	private Integer id;
	private Integer uid;// 用户id
	private Integer tid;// 球队id
	private String remakeName;// 队员在球队的备注名
	private Integer position;// 职位，1：队长，2：副队长，3：教练，4：队员
	private String playerPosition;// 球员位置
	private String leaveRate;// 请假率
	private String signRate;// 出勤率
	private String clothesNum;// 球衣号码
	
	public String getClothesNum() {
		return clothesNum;
	}

	public void setClothesNum(String clothesNum) {
		this.clothesNum = clothesNum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getRemakeName() {
		return remakeName;
	}

	public void setRemakeName(String remakeName) {
		this.remakeName = remakeName;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(String playerPosition) {
		this.playerPosition = playerPosition;
	}

	public String getLeaveRate() {
		return leaveRate;
	}

	public void setLeaveRate(String leaveRate) {
		this.leaveRate = leaveRate;
	}

	public String getSignRate() {
		return signRate;
	}

	public void setSignRate(String signRate) {
		this.signRate = signRate;
	}

}
