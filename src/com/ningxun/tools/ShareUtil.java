package com.ningxun.tools;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;

/**
* 分享工具类
* @author hujian
* @date 2017年7月24日
* @version 1.0
*/
public class ShareUtil {
	
	/**
	 * 生成签名的随机串
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @return
	 */
	public static String getNonceStr() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
	}
	
	/**
	 * 生成签名的时间戳
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	

    /**
	 * 生成签名
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @param nonceStr 随机字符串
	 * @param timeStamp 时间戳
	 * @param url 当前网页的url
	 * @return
	 * @throws Exception
	 */
	public static String getSignature(String nonceStr,String timeStamp,String url){
    	
    	String jsapiTicket = TokenThread.jsapiTicket;
    	String signature="";
    	String str = "jsapi_ticket="+jsapiTicket+"&noncestr="+nonceStr+"&timestamp="+timeStamp+"&url=" + url;
    	//System.out.println(str);
    	try {
    		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
    		crypt.reset();
    		crypt.update(str.getBytes("UTF-8"));
    		signature = byteToHex(crypt.digest());
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	return signature;
    }
    
    private static String byteToHex(final byte[] hash) {
       Formatter formatter = new Formatter();
       for (byte b : hash) {
           formatter.format("%02x", b);
       }
       String result = formatter.toString();
       formatter.close();
       return result;
   }
    
}
