package com.ningxun.security.action;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.ningxun.security.dao.SecurityDAO;

public class SecIncludeTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * author rbh purperse 自定义用户是否具有该组链接访问权限标签 url 需要访问的链接的url
	 */

	private String urls;
	private SecurityDAO mydao = new SecurityDAO();

	@Override
	public int doEndTag() throws JspException {
		// 获取标签体内部数据并输出到控制台
		String content = this.getBodyContent().getString();
		Boolean permision = mydao.findResourseOfUserByUrls(urls);

		if (permision) {
			try {
				this.pageContext.getOut().print(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return EVAL_PAGE;
		}
		return SKIP_BODY;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}
}
