package com.ningxun.security.action;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ningxun.security.dao.SecurityDAO;

//得到用户所拥有的角色和属性值
//@Component这个是必须的
@Component
public class NxUserDetailsService implements UserDetailsService {
	private SecurityDAO securityDAO = new SecurityDAO();

	// 根据username得到userDetails对象，这里传递的参数是openID
	public UserDetails loadUserByUsername(String openId) throws UsernameNotFoundException {
		UserDetails nowUser = null;
		try {
			nowUser = securityDAO.UserInfoService(openId);
		} catch (Exception e) {
			e.printStackTrace();
		}// 给UserDetails赋值
		return nowUser;
	}
}
