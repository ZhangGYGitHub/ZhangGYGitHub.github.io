package com.ningxun.league.vo;

import java.util.Date;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String openId;
	private String unionId;
	private String username;
	private String password;
	private String nickname;
	private Date birthday;
	private String phone;
	private Integer sex;
	private Integer habitfoot;
	private String perAutograph;
	private String synopsis;
	private Integer likeNum;
	private String headPortrait;
	private String headPortraitNew;
	private String height;
	private String weight;
	private String province;
	private String city;
	private Integer isBind;
	private Integer accountNonExpired;
	private Integer credentialsNonExpired;
	private Integer accountNonLocked;
	private Integer enabled;
	private Integer userType;
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String openId, String unionId, String username,
			String password, String nickname, Date birthday, String phone,
			Integer sex, Integer habitfoot, String perAutograph,
			String synopsis, Integer likeNum, String headPortrait,
			String headPortraitNew, String height, String weight,
			String province, String city, Integer isBind,
			Integer accountNonExpired, Integer credentialsNonExpired,
			Integer accountNonLocked, Integer enabled, Integer userType,
			Date createTime, Integer createUser, Date modifyTime,
			Integer modifyUser, Date deleteTime, Integer deleteUser,
			Integer delState) {
		this.openId = openId;
		this.unionId = unionId;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.birthday = birthday;
		this.phone = phone;
		this.sex = sex;
		this.habitfoot = habitfoot;
		this.perAutograph = perAutograph;
		this.synopsis = synopsis;
		this.likeNum = likeNum;
		this.headPortrait = headPortrait;
		this.headPortraitNew = headPortraitNew;
		this.height = height;
		this.weight = weight;
		this.province = province;
		this.city = city;
		this.isBind = isBind;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.enabled = enabled;
		this.userType = userType;
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

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getHabitfoot() {
		return this.habitfoot;
	}

	public void setHabitfoot(Integer habitfoot) {
		this.habitfoot = habitfoot;
	}

	public String getPerAutograph() {
		return this.perAutograph;
	}

	public void setPerAutograph(String perAutograph) {
		this.perAutograph = perAutograph;
	}

	public String getSynopsis() {
		return this.synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Integer getLikeNum() {
		return this.likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public String getHeadPortrait() {
		return this.headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getHeadPortraitNew() {
		return this.headPortraitNew;
	}

	public void setHeadPortraitNew(String headPortraitNew) {
		this.headPortraitNew = headPortraitNew;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getIsBind() {
		return this.isBind;
	}

	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}

	public Integer getAccountNonExpired() {
		return this.accountNonExpired;
	}

	public void setAccountNonExpired(Integer accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Integer getCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Integer credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Integer getAccountNonLocked() {
		return this.accountNonLocked;
	}

	public void setAccountNonLocked(Integer accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
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

}