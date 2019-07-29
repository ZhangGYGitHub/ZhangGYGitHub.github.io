package com.ningxun.security.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

//加载资源与权限的对应关系，并判断系统资源是否包含请求资源
public class NxSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
//	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private boolean isThrough = true;// 当系统资源不包含请求资源时是允许通过还是禁止通过,true:只判断系统配置的资源，false：系统未配置的资源一概交由后面的过滤器来判断

	// 初始化系统资源
	public NxSecurityMetadataSource() {
		// 这里程序加载了系统中的资源
		this.loadResourceDefine();
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	// 加载所有资源与角色权限的对应关系
	private void loadResourceDefine() {
		try {
			if (resourceMap == null) {
				resourceMap = new HashMap<String, Collection<ConfigAttribute>>();// 资源和角色的对应关系
			}
		} catch (Exception e) {
			System.err.println("出现异常！！！");
		}
	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();// 请求的资源
		//--------------------------------------此处加上资源访问统计----------------------------------------
		if (isThrough) {// 放行
			return null;
		} else {
			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();// 角色列表
			if (resourceMap.get(requestUrl) == null) {// 请求的资源系统中没有对应项，则构造一个虚拟角色，然后由LhAccessDecisionManager进行拦截
				ConfigAttribute configAttribute = new SecurityConfig("STUDENT_ROLE");// 意味着角色命名不要为 STUDENT_ROLE
				configAttributes.add(configAttribute);
			}
			return configAttributes;
		}
	}

	public static Map<String, Collection<ConfigAttribute>> getResourceMap() {
		return resourceMap;
	}

	public static void setResourceMap(Map<String, Collection<ConfigAttribute>> resourceMap) {
		NxSecurityMetadataSource.resourceMap = resourceMap;
	}
}