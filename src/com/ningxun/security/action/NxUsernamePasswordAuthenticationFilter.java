package com.ningxun.security.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class NxUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	public static final String VALIDATE_CODE = "verifyCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		// 获取微信返回的code码
		String openId = request.getParameter("openId").trim();	
		
		try {
			
			UsernamePasswordAuthenticationToken authRequest;
			
			authRequest = new UsernamePasswordAuthenticationToken(openId, "");
			// 允许子类设置详细属性
			setDetails(request, authRequest);
			// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
			return this.getAuthenticationManager().authenticate(authRequest);
			
		} catch (Exception e) {
			logger.error(e);
			throw new AuthenticationServiceException("登录出错");
		}
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
}
