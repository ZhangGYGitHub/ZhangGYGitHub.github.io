package com.ningxun.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 
* <li>@ClassName: HttpClientUtils<br/>
* <li>创建时间：2017-7-20 上午9:12:15<br/>
*
* 描述:http请求<br/>
* @author Administrator
 */
public class HttpClientUtils {

	/**
	 * 执行HTTP GET请求，参数封装在URL
	 * 
	 * @param url
	 * @return
	 */
	public static String httpGet(String url) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.getParams().setContentCharset("UTF-8");

		String result = null;
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				result = method.getResponseBodyAsString();
			} else {
				// logger.info("Method failed: " + method.getStatusLine());
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问�?
			// logger.info("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			// logger.info("发生网络异常");
			e.printStackTrace();
		} finally {
			// 释放连接
			if (method != null)
				method.releaseConnection();
			method = null;
			client = null;
		}
		return result;
	}
	/**
	 * 执行post请求 参数JSON格式
	 */
		public static String postJSON(String strUrl,String param){
//			String params=JSONObject.fromObject(param).toString();
			try {
				URL url = new URL(strUrl);// 创建连接
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setUseCaches(false);
				connection.setInstanceFollowRedirects(true);
				connection.setRequestMethod("POST"); // 设置请求方式
				connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格�?
				connection.setRequestProperty("Content-Type", "application/json"); // 设置发�?数据的格�?
				connection.connect();
				OutputStreamWriter out = new OutputStreamWriter(
						connection.getOutputStream(), "UTF-8"); // utf-8编码
				out.append(param);
				out.flush();
				out.close();
				// 读取响应
				int length = (int) connection.getContentLength();// 获取长度
				InputStream is = connection.getInputStream();
				if (length != -1) {
					byte[] data = new byte[length];
					byte[] temp = new byte[512];
					int readLen = 0;
					int destPos = 0;
					while ((readLen = is.read(temp)) > 0) {
						System.arraycopy(temp, 0, data, destPos, readLen);
						destPos += readLen;
					}
					String result = new String(data, "UTF-8"); // utf-8编码
					return result;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	/**
	 * 执行HTTP POST请求，参数用map对象传�?
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected static String httpPost(String url, Map<String, Object> paramMap) {
		String content = null;
		if (url == null || url.trim().length() == 0)
			return null;
		HttpClient httpClient = new HttpClient();
		
		httpClient.getParams().setContentCharset("UTF-8");
		// 设置header
		httpClient
				.getParams()
				.setParameter(
						HttpMethodParams.USER_AGENT,
						"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");

		// 代理设置
		// httpClient.getHostConfiguration().setProxy("128.128.176.74", 808);

		PostMethod method = new PostMethod(url);
		if (paramMap != null) {
			Iterator it = paramMap.keySet().iterator();

			while (it.hasNext()) {
				String key = it.next() + "";
				Object o = paramMap.get(key);
				if (o != null && o instanceof String) {
					method.addParameter(new NameValuePair(key, o.toString()));
				}
				if (o != null && o instanceof String[]) {
					String[] s = (String[]) o;
					if (s != null)
						for (int i = 0; i < s.length; i++) {
							method.addParameter(new NameValuePair(key, s[i]));
						}
				}
			}
		}
		
		try {
			// logger.info("method.getPath="+method.getQueryString());
			// logger.info("getURI="+method.getURI());
			httpClient.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				content = method.getResponseBodyAsString();
			} else {
				// logger.info("Method failed: " + method.getStatusLine());
			}
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (method != null)
				method.releaseConnection();
			method = null;
			httpClient = null;
		}
		return content;
	}

	/**
	 * HTTP GET请求参数封装(将参数添加到URL�?
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String urlParamPac(String url, Map<String, Object> paramMap) {
		StringBuilder stringBuilder = new StringBuilder(url + "?");
		Iterator it = paramMap.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next() + "";
			Object value = paramMap.get(key);
			stringBuilder.append(key + "=" + value + "&");
		}

		return stringBuilder.substring(0, stringBuilder.length() - 1);
	}
	
	@SuppressWarnings("rawtypes")
	protected static String urlParamSet(String url, Map<String, Object> paramMap) {
		StringBuilder stringBuilder = new StringBuilder(url);
		Iterator it = paramMap.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next() + "";
			Object value = paramMap.get(key);
			stringBuilder.append(key + "=" + value + "&");
		}

		return stringBuilder.substring(0, stringBuilder.length() - 1);
	}

	public static void main(String[] args) {
//		String URL = "http://116.204.64.165:7880/IWMS/PayNotify/ccbpayCallBack";
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("success", true);
//		map.put("code", "123");
//		System.out.println(HttpClientUtils.httpPost(URL, map));
		
		String Url="http://119.79.224.116:20014/JLESServer/POST_SCM_DJ?Type=Post_EsOrder&USERNAME=JLSOFT&PASSWORD=88888&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"4d4d7828d59fd5f797ea7bccae99c9ec\"}&JSONXML={\"OrderId\":\"201312050001\",\"created\":\"2013-11-11\",\"modified\":\"2013-11-11\",\"Pay_time\":\"2013-11-11\",\"end_time\":\"2013-11-11\",\"Status\":\"等待卖家发货\",\"shipping_type\":\"express\",\"title\":\"广百官方商城\",\"seller_nick\":\"广百官方商城\",\"buyer_nick\":\"lizhiqiang\",\"total_fee\":\"2000\",\"payment\":\"1800\",\"adjust_fee\":\"0\",\"discount_fee\":\"200\",\"point_fee\":\"0\",\"buyer_obtain_point_fee\":\"0\",\"post_fee\":\"100\",\"seller_memo	\":\"已审\",\"buyer_message\":\"请尽快安排物流发货，谢谢\",\"receiver_name\":\"李志强\",\"receiver_state\":\"湖北省\",\"receiver_city\":\"武汉市\",\"receiver_district\":\"硚口区\",\"receiver_address\":\"汉正�?90号\",\"receiver_zip\":\"430033\",\"receiver_mobile\":\"13419644646\",\"receiver_phone\":\" \",\"invoice\":\"1\",\"invoice_name\":\"武汉金力软件有限公司\",\"SysTemCon\":\"1\",\"SPLIST\":[{\"OrderItemId\":\"20131205000101\",\"sku_id\":\"123456789\",\"ProductID\":\"000001\",\" sp_title \":\"格力2匹空调\",\"Price \":\"2000\",\"num\":\"1\",\"total_fee\":\"0\",\" adjust_fee_ITEM\":\"2000\",\"discount_fee_item\":\"200\",\"payment_item\":\"1800,\"WhNo \":\"01019901\" }]}";
		System.out.println(HttpClientUtils.httpGet(Url));
	}
}
