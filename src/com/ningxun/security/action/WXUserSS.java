package com.ningxun.security.action;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: WXUserSS.java<br/>
 *<li>创建时间: 2017-8-2 下午2:41:34<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
public class WXUserSS implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private int id;// 微信用户id
	private String openId;// 微信用户对应公众号的openID
	private String unionId;// 微信用户PC登录对应公众号的unionId
	private String username;// 用户登录用户名
	private String password;// 用户登录密码
	private String nickname;// 用户昵称
	private Integer sex;// 用户性别
	private String headPortrait;// 用户原头像
	private String headPortraitNew;// 用户现头像
	private Integer isBind;// 用户是否与微信绑定
	private int userType;// 0：自注册用户；1：集体注册用户；2：平台管理员；3：区域管理员
	private boolean enabled;// 可用状态：1、不可用；0、可用
	
	private boolean accountNonExpired;// 用户账号是否过期：1、过期；0、未过期
	private boolean accountNonLocked;// 用户账号是否被锁定:1、锁定；0、未锁定
	private boolean credentialsNonExpired;// 用户密码是否过期：1、过期；0、未过期
	
	
	public WXUserSS(String openId, String username, String password,
			Integer isBind, int userType, boolean enabled,
			boolean accountNonExpired, boolean accountNonLocked,
			boolean credentialsNonExpired) {
		this.openId = openId;
		this.username = username;
		this.password = password;
		this.isBind = isBind;
		this.userType = userType;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
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

	public Collection<GrantedAuthority> getAuthorities() {
		return null;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getHeadPortraitNew() {
		return headPortraitNew;
	}

	public void setHeadPortraitNew(String headPortraitNew) {
		this.headPortraitNew = headPortraitNew;
	}

	public Integer getIsBind() {
		return isBind;
	}

	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

}
