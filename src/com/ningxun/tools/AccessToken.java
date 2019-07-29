package com.ningxun.tools;

/**
 * 
* <li>@ClassName: AccessToken<br/>
* <li>创建时间：2017-7-12 下午1:06:31<br/>
*
* 描述:接口访问凭证<br/>
* @author Administrator
 */
public class AccessToken {
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}