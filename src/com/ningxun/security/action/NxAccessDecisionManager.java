package com.ningxun.security.action;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

//判断用户是否具有所请求资源所需要的角色权限
public class NxAccessDecisionManager implements AccessDecisionManager {
	// 当用户不具有访问请求资源的权限时是允许通过还是禁止通过
//	private boolean isThrough = false;

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
//		if (configAttributes == null) {
//			// 如果角色列表为空则放行，相当于系统不对请求资源设置安全屏障
//			return;
//		}
//
//		// 对应请求资源的系统所有角色权限
//		Iterator<ConfigAttribute> iterator = configAttributes.iterator();// configAttributes由LhSecurityMetadataSource得到
//		while (iterator.hasNext()) {
//			ConfigAttribute configAttribute = iterator.next();
//			// 访问所请求资源所需要的角色权限
//			String needPermission = configAttribute.getAttribute();
//			// 用户所拥有的权限authentication
//			for (GrantedAuthority ga : authentication.getAuthorities()) {// authentication由LhUserDetailsService得到
//				if (needPermission.equalsIgnoreCase(ga.getAuthority())) {// 不区分大小写，但security的xml文件里角色依然区分大小写
//					return;
//				}
//			}
//		}
//		if (isThrough) {// 相当于没有安全控制
//			return;
//		} else {// 没有角色权限禁止访问,没有权限抛出异常,禁止访问
//			throw new AccessDeniedException(" 没有权限访问！");
//		}
		return;
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
