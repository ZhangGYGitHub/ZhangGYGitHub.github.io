package com.ningxun.tools;
/**
 * 
* <li>@ClassName: TokenThread<br/>
* <li>创建时间：2017-7-12 下午1:05:32<br/>
*
* 描述:定时获取微信access_token的线程<br/>
* @author Administrator
 */
public class TokenThread implements Runnable {
	
	public static AccessToken accessToken = null;
	public static String jsapiTicket = null;

	public void run() {
		while (true) {
			try {
				// 获取token
				accessToken = WeixinUtil.getAccessToken(Config.getAppid(), Config.getAppsecret());
				jsapiTicket = WeixinUtil.getTicket();
				if (null != accessToken) {
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}
			} catch (Exception e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					//log.error("{}", e1);
				}
				//log.error("{}", e);
			}
		}
	}
}
