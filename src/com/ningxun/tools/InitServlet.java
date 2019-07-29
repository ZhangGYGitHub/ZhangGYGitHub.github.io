package com.ningxun.tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.HibernateDAO;
import com.ningxun.wxuserinfo.vo.WXUser;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: InitServlet.java<br/>
 *<li>创建时间: 2017-8-4 上午8:40:44<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	
	// 用于记录日志
	private Log log = LogFactory.getLog(getClass());

	@Override
	public void init() throws ServletException {
		System.out.println("*********请求session资源***********");
		try {
			HibernateDAO.findById(WXUser.class, 1);
			System.out.println("*********请求资源完成**********");
		} catch (Exception e) {
			System.out.println("*********请求资源失败**********");
			log.error(e);
		}
	}
}
