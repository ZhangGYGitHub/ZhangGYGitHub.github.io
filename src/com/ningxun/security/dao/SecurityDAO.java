package com.ningxun.security.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ningxun.base.user.vo.Userinfo;
import com.ningxun.common.HibernateDAO;
import com.ningxun.security.action.NxSecurityMetadataSource;
import com.ningxun.security.action.UserInfoSS;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.wxuserinfo.dao.WXUserDAO;
import com.ningxun.wxuserinfo.vo.WXUser;


public class SecurityDAO {

	/**
	 * 描述: 根据用户id获取资源<br/>
	 * 
	 * @return List<String> 返回类型 创建时间：2016年7月18日/下午1:32:02<br/>
	 * @author Administrator
	 * @version V1.0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> findRolesByUserID(int userid) throws Exception {
		String sql = "select  r.rolename "// "select r.rolename "加载用户信息时加载用户id
				+ "from role r, userinforole ur " + "where r.id=ur.roleid and ur.userInfoid= " + userid;

		List<String> tmpList = HibernateDAO.findBySql(sql);
		return tmpList;
	}

	/**
	 * 描述: 根据资源链接跟新资源<br/>
	 * 
	 * @return Boolean 返回类型 创建时间：2016年7月18日/上午11:53:25<br/>
	 * @author Administrator
	 * @version V1.0
	 * @throws Exception
	 */
	public Boolean updateResourse(String resLink) throws Exception {
		String sql = "update  resources set clicks = (select t.clicks + 1 from (select clicks from resources  where resLink = '" + resLink + "') t )" + " where resLink='"
				+ resLink + "'";
		HibernateDAO.executeBySql(sql);
		return true;
	}

	/**
	 * 描述: 根据用户名获取用户对象<br/>
	 * 
	 * @return Userinfo 返回类型 创建时间：2016年7月18日/下午12:14:49<br/>
	 * @author Administrator
	 * @version V1.0
	 * @throws Exception
	 */
	public Userinfo findUserInfoByUserName(String userName) throws Exception {
		String hql = "from Userinfo where userName='" + userName + "'";
		Userinfo userInfo = (Userinfo) HibernateDAO.findByHql(hql).get(0);
		return userInfo;
	}

	/**
	 * 描述: 提取说有资源<br/>
	 * 
	 * @return List<Resources> 返回类型 创建时间：2016年7月18日/上午11:55:03<br/>
	 * @author Administrator
	 * @version V1.0
	 * @throws Exception
	 */
//	@SuppressWarnings("unchecked")
//	public List<Resources> findAllResourse() throws Exception {
//		String sql = "select * from resources where enabled=0";
//		List<Resources> resourseList = HibernateDAO.findBySql(sql, Resources.class);
//		return resourseList;
//	}

	/**
	 * 描述: 通过资源id来获取拥有该资源的角色<br/>
	 * 
	 * @return List<String> 返回类型 创建时间：2016年7月18日/上午11:56:55<br/>
	 * @author Administrator
	 * @version V1.0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> findRolesByResourse(Integer resourseid) throws Exception {
		String sql = "select ro.rolename from role ro,roleresource rr  where rr.roleid=ro.id and rr.resourceid=" + resourseid;
		List<String> roles = HibernateDAO.findBySql(sql);
		return roles;
	}

	/*
	 * 根据用户请求的url来判断用户是否具有访问该链接的权限
	 * 
	 * @string url用户请求的url 1、如果库里边没有这个资源则返回true 2、如果用户中存在改资源但该用户所具有的角色不存在该资源则返回false
	 */
	public Boolean findResourseOfUserByUrl(String url) {
		UserInfoSS userDetails = (UserInfoSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
		/* 此处放开平台管理员，使其可以观看所有内容 */
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("平台管理员")) {
				return true;
			}
		}
		/* end 放开管理员 */

		Map<String, Collection<ConfigAttribute>> mymap = NxSecurityMetadataSource.getResourceMap();
		Collection<ConfigAttribute> config = mymap.get(url + "*");

		if (config != null) {
			for (GrantedAuthority authority : authorities) {
				ConfigAttribute configAttribute = new SecurityConfig(authority.getAuthority());
				if (config.contains(configAttribute)) {
					return true;
				}

			}
			return false;
		}

		return false;
	}

	/*
	 * 根据用户请求的url来判断用户是否具有访问该链接的权限
	 * 
	 * @string url用户请求的url 1、如果库里边没有这个资源则返回true 2、如果用户中存在改资源但该用户所具有的角色不存在该资源则返回false
	 */
	public Boolean findResourseOfUserByUrls(String includeUrl) {
		UserInfoSS userDetails = (UserInfoSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
		/* 此处放开平台管理员，使其可以观看所有内容 */
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("平台管理员")) {
				return true;
			}
		}
		/* end 放开管理员 */

		Map<String, Collection<ConfigAttribute>> mymap = NxSecurityMetadataSource.getResourceMap();
		String urls[] = includeUrl.split(",");
		for (String url : urls) {
			Collection<ConfigAttribute> config = mymap.get(url + "*");
			if (config != null) {
				for (GrantedAuthority authority : authorities) {
					ConfigAttribute configAttribute = new SecurityConfig(authority.getAuthority());
					if (config.contains(configAttribute)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void resetResourceMap() throws Exception {
		NxSecurityMetadataSource.getResourceMap().clear();
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();// 资源和角色的对应关系;
		System.out.println("-------------重载权限-------------");
//		List<Resources> resList = (List<Resources>) findAllResourse();
//		for (Resources res : resList) {// 按资源寻找有权限的角色
//			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();// 角色列表
//			List<String> roles = findRolesByResourse(res.getId());
//			for (String role : roles) {// 拥有特定资源的所有角色
//				ConfigAttribute configAttribute = new SecurityConfig(role);
//				configAttributes.add(configAttribute);
//			}
//			resourceMap.put(res.getResLink() + "*", configAttributes);// 将资源和角色存为一对多的map结构
//			// configAttributes.removeAll(configAttributes);
//		}
		System.out.println("-------------重载完成-------------");
		NxSecurityMetadataSource.setResourceMap(resourceMap);
	}

	/**
	 * 描述: 根据用户名得到spring中的UserDetails对象<br/>
	 * 
	 * @param username
	 * @return UserDetails 返回类型 创建时间：2016年7月21日/上午11:42:58<br/>
	 * @author Administrator
	 * @throws Exception
	 */
	public UserDetails UserInfoService(String openId) throws Exception {
		
		WXUserSS userdetail;
		WXUserDAO wxUserDAO = new WXUserDAO();
		WXUser tempUserInfo = wxUserDAO.findWxUserByOpenId(openId);
		if (tempUserInfo == null) {
			// 这里跳转注册方法。。。。
			userdetail = new WXUserSS(openId, "", "", 1, 1, false, false, false, false);
			
			return userdetail;
		} else {
			// 给spring security的userdetail赋值
			userdetail = new WXUserSS(openId, "", "", 1, 1, tempUserInfo.getEnabled() == 0 ? true : false, tempUserInfo.getAccountNonExpired() == 0 ? true : false,
					tempUserInfo.getCredentialsNonExpired() == 0 ? true : false, tempUserInfo.getAccountNonLocked() == 0 ? true : false);
			userdetail.setId(tempUserInfo.getId());
			userdetail.setOpenId(tempUserInfo.getOpenId());
			userdetail.setUnionId(tempUserInfo.getUnionId());
			userdetail.setUsername(tempUserInfo.getUsername());
			userdetail.setPassword(userdetail.getPassword());
			userdetail.setNickname(tempUserInfo.getNickname());
			userdetail.setHeadPortrait(tempUserInfo.getHeadPortrait());
			userdetail.setHeadPortraitNew(tempUserInfo.getHeadPortraitNew());
			// userdetail.setUserType(tempUserInfo.getUserType());// 0：自注册用户；1：集体注册用户；2：平台管理员；3：区域管理员
			return userdetail;
		}
	}
}
