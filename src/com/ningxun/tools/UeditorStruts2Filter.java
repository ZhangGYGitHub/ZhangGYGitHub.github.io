package com.ningxun.tools;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * 
 * <li>@ClassName: UeditorStruts2Filter<br/> <li>创建时间：2016年7月28日 下午4:03:16<br/>
 * 
 * 描述:百度编辑器的Struts2过滤器<br/>
 * 
 * @author Administrator
 */
public class UeditorStruts2Filter extends StrutsPrepareAndExecuteFilter {

	/**
	 * 获取请求并分发过滤的
	 */
	@SuppressWarnings("unused")
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		if (url.endsWith("controller.jsp")) {
			// 表名是百度的编辑器
			chain.doFilter(req, res);
		} else {
			// 正常的Struts2请求
			super.doFilter(req, res, chain);
		}
	}
}
