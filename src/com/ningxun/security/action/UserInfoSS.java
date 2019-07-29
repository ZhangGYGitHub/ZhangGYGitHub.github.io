package com.ningxun.security.action;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfoSS implements UserDetails {

	private static final long serialVersionUID = -6640766834888660209L;
	private int id;// userinfo表的ID
	private String userName;// 用户登录账号
	private String realName;// 用户真实姓名
	private String password;// 用户密码
	private String email;// 电子邮箱
	private String mobilNo;// 手机号
	private int userType;// 0：自注册用户；1：集体注册用户；2：平台管理员；3：区域管理员
	private boolean enabled;// 可用状态：1、不可用；0、可用
	private Collection<GrantedAuthority> authorities;// 用户权限
	
	private boolean accountNonExpired;// 用户账号是否过期：1、过期；0、未过期
	private boolean accountNonLocked;// 用户账号是否被锁定:1、锁定；0、未锁定
	private boolean credentialsNonExpired;// 用户密码是否过期：1、过期；0、未过期
	
	
	private String companyLevel;//所属级别
	private String companyLevel1;//一级
	private String companyLevelName1;
	private String companyLevel2;//二级
	private String companyLevelName2;
	private String companyLevel3;//三级
	private String companyLevelName3;
	private String companyLevel4;//四级
	private String companyLevelName4;
	
	public UserInfoSS(String userName, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<GrantedAuthority> authorities) {

		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = authorities;
	}

	// ---------安全接口用到的属性------------------------------------------------------------------------------------
	/**
	 * 用户账号是否过期：1、过期；0、未过期
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	/**
	 * 用户账号是否被锁定:1、锁定；0、未锁定
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	/**
	 * 用户密码是否过期：1、过期；0、未过期
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * 取得用户登录账号
	 * 
	 * @return
	 */
	public String getUsername() {
		return userName;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	// ---------get--set------------------------------------------------------------------------------------

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilNo() {
		return mobilNo;
	}

	public void setMobilNo(String mobilNo) {
		this.mobilNo = mobilNo;
	}

	/**
	 * 取得用户类型： 0：自注册用户；1：集体注册用户；2：平台管理员；3：区域管理员
	 * 
	 * @return
	 */
	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyLevel() {
		return companyLevel;
	}

	public void setCompanyLevel(String companyLevel) {
		this.companyLevel = companyLevel;
	}

	public String getCompanyLevel1() {
		return companyLevel1;
	}

	public void setCompanyLevel1(String companyLevel1) {
		this.companyLevel1 = companyLevel1;
	}

	public String getCompanyLevelName1() {
		return companyLevelName1;
	}

	public void setCompanyLevelName1(String companyLevelName1) {
		this.companyLevelName1 = companyLevelName1;
	}

	public String getCompanyLevel2() {
		return companyLevel2;
	}

	public void setCompanyLevel2(String companyLevel2) {
		this.companyLevel2 = companyLevel2;
	}

	public String getCompanyLevelName2() {
		return companyLevelName2;
	}

	public void setCompanyLevelName2(String companyLevelName2) {
		this.companyLevelName2 = companyLevelName2;
	}

	public String getCompanyLevel3() {
		return companyLevel3;
	}

	public void setCompanyLevel3(String companyLevel3) {
		this.companyLevel3 = companyLevel3;
	}

	public String getCompanyLevelName3() {
		return companyLevelName3;
	}

	public void setCompanyLevelName3(String companyLevelName3) {
		this.companyLevelName3 = companyLevelName3;
	}

	public String getCompanyLevel4() {
		return companyLevel4;
	}

	public void setCompanyLevel4(String companyLevel4) {
		this.companyLevel4 = companyLevel4;
	}

	public String getCompanyLevelName4() {
		return companyLevelName4;
	}

	public void setCompanyLevelName4(String companyLevelName4) {
		this.companyLevelName4 = companyLevelName4;
	}


	
}
