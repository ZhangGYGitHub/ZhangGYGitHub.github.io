package com.ningxun.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ningxun.tools.Config;
import com.ningxun.tools.TimerManager;
import com.ningxun.tools.TokenThread;

/**
 * <li>技术支持:河北凝讯科技有限公司<br/> <li>项目名称: rongqiu<br/> <li>文件名:
 * GetTokenListener.java<br/> <li>创建时间: 2017-7-20 上午8:36:55<br/>
 * 
 * @author zyt
 * @version [v1.00]
 */
public class GetTokenListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0){

		// 未配置appid、appsecret时给出提示
		try {
			if ("".equals(Config.getAppid()) || "".equals(Config.getAppsecret())) {
				System.out.println("appid and appsecret configuration error, please check carefully.==================");
			} else {
				// 启动定时获取access_token的线程
				new Thread(new TokenThread()).start();
				new TimerManager();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
