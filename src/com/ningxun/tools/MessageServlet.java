package com.ningxun.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningxun.sign.action.MessageUtil;
import com.ningxun.sign.action.signAction;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: MessageServlet.java<br/>
 *<li>创建时间: 2017-8-18 下午4:22:30<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
public class MessageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//doGet(req, resp);
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		// 如果对比结果一致，将回调参数返回
		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		
		StringBuffer sb = new StringBuffer();
		InputStream is = req.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
		
		Map<String, String> map = MessageUtil.xmlToMap(xml);
		
		/********************这里接收消息*****************/
		String event = map.get("Event");
		if (event != null && !event.trim().equals("")) {
			if (event.equals("ShakearoundUserShake")) {
				signAction signAction = new signAction();
				signAction.shakeSign(xml);
			}
		}
		
		//System.out.println(string);
		// 关闭流
		out.close();
	}
}
