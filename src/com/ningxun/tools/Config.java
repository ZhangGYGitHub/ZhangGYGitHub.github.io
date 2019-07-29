package com.ningxun.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ningxun.common.InitListener;
import com.ningxun.common.SysConfig;

/**
 * 配置文件工具类
 * @author hujian
 * @date 2017年7月31日
 * @version 1.0
 */
public class Config {
	
	/**
	 * 从配置文件中获取全局上传文件路径
	 * @author hujian
	 * @date 2017年7月31日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public static String getFileUploadPath() throws Exception{
		String upPath = null;
		Map<String, String> baseDataMap = InitListener.getBaseDataMap();
		if (baseDataMap == null || baseDataMap.size() == 0) {
			// 没有加载到内存
			//从配置文件中读取参数
			//参数一:配置文件中的标签名
			//参数二:标签中的属性名
			upPath = SysConfig.getConfigData("fileUp", "upPath");
		} else {
			// 直接内存读取内存
			upPath = baseDataMap.get("upPath");
		}
		return upPath;
	}
	
	/**
	 * 从配置文件中获取全局Appid
	 * @author hujian
	 * @date 2017年7月31日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public static String getAppid() throws Exception{
		return SysConfig.getConfigData("WechatPay", "appid");
	}
	
	/**
	 * 从配置文件中获取全局Appsecret
	 * @author hujian
	 * @date 2017年7月31日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public static String getAppsecret() throws Exception{
		return SysConfig.getConfigData("WechatPay", "appsecret");
	}
	
	/**
	 * 判断用户是否关注了公众号
	 * @author hujian
	 * @date 2017-9-14
	 * @version 1.0
	 * @param token 
	 * @param openid
	 * @return 返回true 表示已经关注      false 表示还没有关注
	 * @throws IOException 
	 */
	public static boolean judgeIsFollow(String token,String openid) throws IOException{
	    //值为0时，代表此用户没有关注该公众号
		String subscribe = "0" ;
		InputStream is = null ;
	    try {  
	    	String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openid+"&lang=zh_CN";
	            URL urlGet = new URL(url);  
	            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
	            http.setRequestMethod("GET"); // 必须是get方式请求  
	            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
	            http.setDoOutput(true);  
	            http.setDoInput(true);  
	            http.connect();  
	            is = http.getInputStream();  
	            int size = is.available();  
	            byte[] jsonBytes = new byte[size];  
	            is.read(jsonBytes);  
	            String message = new String(jsonBytes, "UTF-8");  
	            JSONObject demoJson = JSONObject.fromObject(message);  
	            //System.out.println("JSON字符串："+demoJson);  
	            subscribe = demoJson.getString("subscribe");
	    } catch (Exception e) {  
	            e.printStackTrace();  
	    } finally {
	    	if (is != null ) {
	    		 is.close();  
			}
	    }
	    return ("1".equals(subscribe));
	}
	
	
	
}
