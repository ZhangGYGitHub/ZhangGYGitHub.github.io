package com.ningxun.base.user.vo;

import java.util.Date;

/**
 * Userinfo entity. @author MyEclipse Persistence Tools
 */

public class Userinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userName;
	private String realName;
	private String password;
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
	private String companyLevel;
	private String companyLevelName;
	private String companyLevel1;
	private String companyLevelName1;
	private String companyLevel2;
	private String companyLevelName2;
	private String companyLevel3;
	private String companyLevelName3;
	private String companyLevel4;
	private String companyLevelName4;
	private String email;
	private String phone;
	private String mobilNo;
	private Integer sex;
	private String address;
	private Date birthday;
	private String faxNo;
	private String postCode;
	private String zhaoPianCurrentFileName;
	private String zhaoPianOriginalFileName;
	private String nationName;
	private String description;
	private String qq;
	private String emergencyContact;

	// Constructors

	/** default constructor */
	public Userinfo() {
	}

	/** full constructor */
	public Userinfo(String userName, String realName, String password,
			Integer accountNonExpired, Integer credentialsNonExpired,
			Integer accountNonLocked, Integer enabled, Integer userType,
			Date createTime, Integer createUser, Date modifyTime,
			Integer modifyUser, Date deleteTime, Integer deleteUser,
			String companyLevel, String companyLevelName, String companyLevel1,
			String companyLevelName1, String companyLevel2,
			String companyLevelName2, String companyLevel3,
			String companyLevelName3, String companyLevel4,
			String companyLevelName4, String email, String phone,
			String mobilNo, Integer sex, String address, Date birthday,
			String faxNo, String postCode, String zhaoPianCurrentFileName,
			String zhaoPianOriginalFileName, String nationName,
			String description, String qq, String emergencyContact) {
		this.userName = userName;
		this.realName = realName;
		this.password = password;
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
		this.companyLevel = companyLevel;
		this.companyLevelName = companyLevelName;
		this.companyLevel1 = companyLevel1;
		this.companyLevelName1 = companyLevelName1;
		this.companyLevel2 = companyLevel2;
		this.companyLevelName2 = companyLevelName2;
		this.companyLevel3 = companyLevel3;
		this.companyLevelName3 = companyLevelName3;
		this.companyLevel4 = companyLevel4;
		this.companyLevelName4 = companyLevelName4;
		this.email = email;
		this.phone = phone;
		this.mobilNo = mobilNo;
		this.sex = sex;
		this.address = address;
		this.birthday = birthday;
		this.faxNo = faxNo;
		this.postCode = postCode;
		this.zhaoPianCurrentFileName = zhaoPianCurrentFileName;
		this.zhaoPianOriginalFileName = zhaoPianOriginalFileName;
		this.nationName = nationName;
		this.description = description;
		this.qq = qq;
		this.emergencyContact = emergencyContact;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCompanyLevel() {
		return this.companyLevel;
	}

	public void setCompanyLevel(String companyLevel) {
		this.companyLevel = companyLevel;
	}

	public String getCompanyLevelName() {
		return this.companyLevelName;
	}

	public void setCompanyLevelName(String companyLevelName) {
		this.companyLevelName = companyLevelName;
	}

	public String getCompanyLevel1() {
		return this.companyLevel1;
	}

	public void setCompanyLevel1(String companyLevel1) {
		this.companyLevel1 = companyLevel1;
	}

	public String getCompanyLevelName1() {
		return this.companyLevelName1;
	}

	public void setCompanyLevelName1(String companyLevelName1) {
		this.companyLevelName1 = companyLevelName1;
	}

	public String getCompanyLevel2() {
		return this.companyLevel2;
	}

	public void setCompanyLevel2(String companyLevel2) {
		this.companyLevel2 = companyLevel2;
	}

	public String getCompanyLevelName2() {
		return this.companyLevelName2;
	}

	public void setCompanyLevelName2(String companyLevelName2) {
		this.companyLevelName2 = companyLevelName2;
	}

	public String getCompanyLevel3() {
		return this.companyLevel3;
	}

	public void setCompanyLevel3(String companyLevel3) {
		this.companyLevel3 = companyLevel3;
	}

	public String getCompanyLevelName3() {
		return this.companyLevelName3;
	}

	public void setCompanyLevelName3(String companyLevelName3) {
		this.companyLevelName3 = companyLevelName3;
	}

	public String getCompanyLevel4() {
		return this.companyLevel4;
	}

	public void setCompanyLevel4(String companyLevel4) {
		this.companyLevel4 = companyLevel4;
	}

	public String getCompanyLevelName4() {
		return this.companyLevelName4;
	}

	public void setCompanyLevelName4(String companyLevelName4) {
		this.companyLevelName4 = companyLevelName4;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilNo() {
		return this.mobilNo;
	}

	public void setMobilNo(String mobilNo) {
		this.mobilNo = mobilNo;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getFaxNo() {
		return this.faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getZhaoPianCurrentFileName() {
		return this.zhaoPianCurrentFileName;
	}

	public void setZhaoPianCurrentFileName(String zhaoPianCurrentFileName) {
		this.zhaoPianCurrentFileName = zhaoPianCurrentFileName;
	}

	public String getZhaoPianOriginalFileName() {
		return this.zhaoPianOriginalFileName;
	}

	public void setZhaoPianOriginalFileName(String zhaoPianOriginalFileName) {
		this.zhaoPianOriginalFileName = zhaoPianOriginalFileName;
	}

	public String getNationName() {
		return this.nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmergencyContact() {
		return this.emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

}