package com.ningxun.team.vo;

import java.util.Date;

import com.ningxun.tools.FormatUtil;

/**
 * @author hujian
 * @date 2017年7月20日
 * @version 1.0
 */
public class Teaminfo {
	
	public static final Integer TEAMINFO_IS_OPEN = 0;
	public static final Integer TEAMINFO_NO_OPEN = 1;

	private Integer id;
	private String name;// 球队名称
	private Integer captainId;// 队长,关联用户id
	private String teamDeclaration;// 球队宣言
	private Integer isOpen;// 是否公开:0是，需要申请加入,1否，只能分享加入
	private String introduce;// 球队介绍
	private String province;// 所在省份
	private String city;// 所在城市
	private String clothesColour;// 队服颜色
	private String regulations;// 规章制度
	private String iconNewName;// 队徽现名
	private String iconOldName;// 队徽原名
	private String sponsor;// 赞助商
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;// 删除状态:0正常,1删除
	
	public String getClothesColour() {
		return clothesColour;
	}

	public void setClothesColour(String clothesColour) {
		this.clothesColour = clothesColour;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCaptainId() {
		return captainId;
	}

	public void setCaptainId(Integer captainId) {
		this.captainId = captainId;
	}
	
	public String getTeamDeclaration() {
		return teamDeclaration;
	}

	public void setTeamDeclaration(String teamDeclaration) {
		this.teamDeclaration = teamDeclaration;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegulations() {
		return regulations;
	}

	public void setRegulations(String regulations) {
		this.regulations = regulations;
	}

	public String getIconNewName() {
		return iconNewName;
	}

	public void setIconNewName(String iconNewName) {
		this.iconNewName = iconNewName;
	}

	public String getIconOldName() {
		return iconOldName;
	}

	public void setIconOldName(String iconOldName) {
		this.iconOldName = iconOldName;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public Date getCreateTime() {
		return createTime;
	}
	
	public String getCreateTimeView() {
		return FormatUtil.formatDate(createTime);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Integer modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Integer getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Integer getDelState() {
		return delState;
	}

	public void setDelState(Integer delState) {
		this.delState = delState;
	}

}
