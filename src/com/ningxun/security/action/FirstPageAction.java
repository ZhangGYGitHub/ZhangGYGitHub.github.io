package com.ningxun.security.action;

import com.ningxun.common.BaseSupportAction;

public class FirstPageAction extends BaseSupportAction {
	private static final long serialVersionUID = 1L;
	private UserInfoSS userInfoSS;

	public String firstPage() {
		userInfoSS = (UserInfoSS) getUser();
		return SUCCESS;
	}
	
	public String welcomePage() {
		userInfoSS = (UserInfoSS) getUser();
		return SUCCESS;
	}

	// ---------get--set------------------------------------------------------------------------------------
	public UserInfoSS getUserInfoSS() {
		return userInfoSS;
	}

	public void setUserInfoSS(UserInfoSS userInfoSS) {
		this.userInfoSS = userInfoSS;
	}

}
