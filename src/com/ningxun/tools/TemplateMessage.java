package com.ningxun.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ningxun.league.vo.League;
import com.ningxun.league.vo.LeagueMatch;
import com.ningxun.league.vo.User;
import com.ningxun.notice.dao.NoticeDao;
import com.ningxun.race.vo.Race;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.train.vo.Train;
import com.ningxun.wxuserinfo.vo.WXUser;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: TemplateMessage.java<br/>
 *<li>创建时间: 2017-8-10 下午8:42:56<br/>
 *
 * @author 梦强
 *@version [v1.00]
 */
public class TemplateMessage {
	
	private NoticeDao noticeDao =new NoticeDao();
	/**
	 * 
	* 描述: 通知模板消息<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-10/下午8:44:05<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void sendMes(String title,String nickName,Integer tid,String action) {
		try {
			// 模板消息ID(“消息发送状态提醒”，模板消息标题)
			String mbID = "mwqRjlHE4XHTAYWrcabuBuV0ppxtKxfdwLLeA0kJHew";
			// 用户的openID
			List<String> openID = noticeDao.findUOpenid(tid);
			// 跳转地址
			String url = "http://tqium.xin/rongqiu/"+action;
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String date = format.format(new Date());
			String first = title;//标题
			String keyword1 = "您有新通知";
			String keyword2 = "成功";
			String keyword3 = date;
			String keyword4 = nickName;
			String remark = "";
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			for (int i = 0; i < openID.size(); i++) {
				String jsoninfo = "{\"template_id\": \"" + mbID
						+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID.get(i)
						+ "\",\"url\": \"" + url + "\", " + "\"data\":"
						+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
						+ "\" },"
						+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
						+ keyword1 + "\"},"
						+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
						+ keyword2 + "\"},"
						+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
						+ keyword3 + "\"},"
						+ " \"keyword4\": {\"color\": \"#173177\",\"value\": \""
						+ keyword4 + "\"},"
						+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
						+ remark + "\" }}}";
				
				HttpClientUtils.postJSON(requestUrl, jsoninfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	* 描述: 摇一摇签到成功模板消息<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-10/下午8:44:05<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public static void shackMes(String mseeage,String nickName,String wxopenID) {
		try {
			// 模板消息ID(“消息发送状态提醒”，模板消息标题)
			String mbID = "mwqRjlHE4XHTAYWrcabuBuV0ppxtKxfdwLLeA0kJHew";
			// 用户的openID
			String openID = wxopenID;
			// 跳转地址
			String url = "";
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String date = format.format(new Date());
			String first = "摇一摇签到通知";//标题
			String keyword1 = mseeage;
			String keyword2 = "成功";
			String keyword3 = date;
			String keyword4 = nickName;
			String remark = "";
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			String jsoninfo = "{\"template_id\": \"" + mbID
					+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID
					+ "\",\"url\": \"" + url + "\", " + "\"data\":"
					+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
					+ "\" },"
					+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
					+ keyword1 + "\"},"
					+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
					+ keyword2 + "\"},"
					+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
					+ keyword3 + "\"},"
					+ " \"keyword4\": {\"color\": \"#173177\",\"value\": \""
					+ keyword4 + "\"},"
					+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
					+ remark + "\" }}}";
			
			HttpClientUtils.postJSON(requestUrl, jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	* 描述: 驳回或忽略申请加入球队向申请人发送模板消息<br/>
	* @param result 申请加入球队处理结果
	* @param applyUser 申请人
	* @param handleUser 处理人
	* @param action 需要跳转的url链接，http开头的全路径
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-10/下午8:52:58<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void sendTMes(String result,WXUser applyUser,WXUser handleUser,String action){
		try {
			// 模板消息ID(“申请审批提醒”，模板消息标题)
			String mbID = "udReoQ0q96H8RhIgvAC7J-VQ6B0Tty8jCkFRwLzrAXM";
			// 用户的openID
			String openID = applyUser.getOpenId();
			// 跳转地址
			String url = action;
			// 模板消息内容（视具体模板消息而定）
			String first = "";//标题
			String keyword1 = "申请加入球队";
			String keyword2 = handleUser.getNickname();
			String keyword3 = result;
			String keyword4 = "未通过";
			String remark = "";
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			String jsoninfo = "{\"template_id\": \"" + mbID
					+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID
					+ "\",\"url\": \"" + url + "\", " + "\"data\":"
					+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
					+ "\" },"
					+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
					+ keyword1 + "\"},"
					+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
					+ keyword2 + "\"},"
					+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
					+ keyword3 + "\"},"
					+ " \"keyword4\": {\"color\": \"#173177\",\"value\": \""
					+ keyword4 + "\"},"
					+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
					+ remark + "\" }}}";
			
			HttpClientUtils.postJSON(requestUrl, jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	* 描述: 申请加入球队通过模板消息<br/>
	* @param captainName 球队队长昵称
	* @param teamName 球队名称
	* @param applyUser 申请人
	* @param action 需要跳转的url链接，http开头的全路径
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-10/下午8:59:29<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void sendSucMes(String captainName,String teamName,WXUser applyUser,String action){
		try {
			// 模板消息ID(“申请加入球队成功”，模板消息标题)
			String mbID = "X0_bO61A9KpSPXkQvtj5gSEEwfVSqzw7_Dx6ldmnJSY";
			// 用户的openID
			String openID = applyUser.getOpenId();
			// 跳转地址
			String url = action;
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String date = format.format(new Date());
			String first = "申请加入球队成功";//标题
			String keyword1 = captainName;
			String keyword2 = teamName;
			String keyword3 = date;
			String remark = "";
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			String jsoninfo = "{\"template_id\": \"" + mbID
					+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID
					+ "\",\"url\": \"" + url + "\", " + "\"data\":"
					+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
					+ "\" },"
					+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
					+ keyword1 + "\"},"
					+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
					+ keyword2 + "\"},"
					+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
					+ keyword3 + "\"},"
					+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
					+ remark + "\" }}}";
			
			HttpClientUtils.postJSON(requestUrl, jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	* 描述: 申请加入球队向管理员发送模板消息<br/>
	* @param applyUser 申请人
	* @param handleUserList 能够处理申请信息的管理员集合
	* @param action 需要跳转的url链接，http开头的全路径
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-10/下午9:04:45<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void sendApplyMes(WXUser applyUser,List<WXUser> handleUserList,String action){
		try {
			// 模板消息ID(“申请结果通知”，模板消息标题)
			String mbID = "LmPd3WvCk0FX1soJ9ER5QKIUXbHjc6D2MypJpNr3tn8";
			// 跳转地址
			String url = action;
			// 模板消息内容（视具体模板消息而定）
			String first = "申请加入球队";//标题
			String keyword1 = applyUser.getNickname()+"申请加入球队";
			String keyword2 = "待处理";
			String remark = "";
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			for (int i = 0; i < handleUserList.size(); i++) {
				String jsoninfo = "{\"template_id\": \"" + mbID
						+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + handleUserList.get(i).getOpenId()
						+ "\",\"url\": \"" + url + "\", " + "\"data\":"
						+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
						+ "\" },"
						+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
						+ keyword1 + "\"},"
						+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
						+ keyword2 + "\"},"
						+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
						+ remark + "\" }}}";
				
				HttpClientUtils.postJSON(requestUrl, jsoninfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* 描述: 通知有人加入联赛<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-24/下午2:31:57<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public static void sendLeagueMes(String nickName,String teamName,String openID) {
		try {
			// 模板消息ID(“消息发送状态提醒”，模板消息标题)
			String mbID = "1kzy7brSRtlRHG_kme9aANrZMV334pkV--yjdvbMZDg";
			// 跳转地址
			String url = "";
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String date = format.format(new Date());
			String first = "您在踢球么创建的联赛有新球队加入";//标题
			String keyword1 = nickName;
			String keyword2 = teamName;
			String keyword3 = date;
			String remark = "快去踢球么系统内查看详情吧！";
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ TokenThread.accessToken.getToken();
			String jsoninfo = "{\"template_id\": \"" + mbID
					+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID
					+ "\",\"url\": \"" + url + "\", " + "\"data\":"
					+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
					+ "\" },"
					+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
					+ keyword1 + "\"},"
					+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
					+ keyword2 + "\"},"
					+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
					+ keyword3 + "\"},"
					+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
					+ remark + "\" }}}";
			
			HttpClientUtils.postJSON(requestUrl, jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	* 描述: 申请加入联赛成功后<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-24/下午3:30:17<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public static void sendLeagueApplyMes(String nickName,String LeagueName,List<WXUser> handleUserList) {
		try {
			// 模板消息ID(“消息发送状态提醒”，模板消息标题)
			String mbID = "LmPd3WvCk0FX1soJ9ER5QKIUXbHjc6D2MypJpNr3tn8";
			// 跳转地址
			String url = "";
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String date = format.format(new Date());
			String first = "您的球队成功加入联赛";//标题
			String keyword1 = nickName+"在"+date+"申请加入"+LeagueName+"联赛";
			String keyword2 = "成功通过";
			String remark = "快去踢球么系统内查看详情吧！";
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ TokenThread.accessToken.getToken();
			for (int i = 0; i < handleUserList.size(); i++) {
				String jsoninfo = "{\"template_id\": \"" + mbID
						+ "\",\"topcolor\": \"#FF0000\",\"touser\": \""+ handleUserList.get(i).getOpenId()
						+ "\",\"url\": \"" + url + "\", " + "\"data\":"
						+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
						+ "\" },"
						+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
						+ keyword1 + "\"},"
						+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
						+ keyword2 + "\"},"
						+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
						+ remark + "\" }}}";
				
					HttpClientUtils.postJSON(requestUrl, jsoninfo);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	* 描述: 比赛发布模板消息<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-9-5/下午10:42:40<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void RaceMessage(Race race,String nickName,Integer tid,String action) {
		try {
			// 模板消息ID(“消息发送状态提醒”，模板消息标题)
			String mbID = "SXw_aGlVGk3GgWEjPaKloELn-zg9aITU2CkSJugfmAI";
			// 用户的openID
			List<String> openID = noticeDao.findUOpenid(tid);
			// 跳转地址
			String url = "http://tqium.xin/rongqiu/"+action;
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String first = "您有新的比赛通知";//标题
			String keyword1 = race.getRaceName();
			String keyword2 = format.format(race.getStartTime());
			String remark = "";
			if (race.getSignType() == 1) {
				remark = "在"+race.getPlace()+"您将与"+race.getOpponent()+"比赛，需要穿"+race.getClothescolour()+"队服、需要签到";
			}else {
				remark = "在"+race.getPlace()+"您将与"+race.getOpponent()+"比赛，需要穿"+race.getClothescolour()+"队服、不需要签到";
			}
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			for (int i = 0; i < openID.size(); i++) {
				String jsoninfo = "{\"template_id\": \"" + mbID
						+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID.get(i)
						+ "\",\"url\": \"" + url + "\", " + "\"data\":"
						+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
						+ "\" },"
						+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
						+ keyword1 + "\"},"
						+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
						+ keyword2 + "\"},"
						+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
						+ remark + "\" }}}";
				
				HttpClientUtils.postJSON(requestUrl, jsoninfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	* 描述: 训练发布通知模板消息<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-9-5/下午10:45:02<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void TrainMessage(Train train,String nickName,Integer tid,String action) {
		try {
			// 模板消息ID(“消息发送状态提醒”，模板消息标题)
			String mbID = "W6aCj2j9Etfcq79bwNZFZAIX32DRDzHXTrrFksjx4Mw";
			// 用户的openID
			List<String> openID = noticeDao.findUOpenid(tid);
			// 跳转地址
			String url = "http://tqium.xin/rongqiu/"+action;
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String first = "训练通知";//标题
			String keyword1 = train.getTrainName();
			String keyword2 = format.format(train.getStartTime());
			String keyword3 = train.getTrainPlace();
			String keyword4 = "";
			if (train.getIssign() == 1) {
				keyword4 ="需要签到";
			}else {
				keyword4 = "不需要签到";
			}
			String remark = train.getTrainContent();
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			for (int i = 0; i < openID.size(); i++) {
				String jsoninfo = "{\"template_id\": \"" + mbID
						+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID.get(i)
						+ "\",\"url\": \"" + url + "\", " + "\"data\":"
						+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
						+ "\" },"
						+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
						+ keyword1 + "\"},"
						+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
						+ keyword2 + "\"},"
						+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
						+ keyword3 + "\"},"
						+ " \"keyword4\": {\"color\": \"#173177\",\"value\": \""
						+ keyword4 + "\"},"
						+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
						+ remark + "\" }}}";
				
				HttpClientUtils.postJSON(requestUrl, jsoninfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* 描述: 申请加入联赛向创建人发送模板消息<br/>
	* @param applyUser 申请人
	* @param creator 联赛创建人
	* @param action 需要跳转的url链接，http开头的全路径
	* 创建时间：2018-3-14
	* @author liudongxin
	* @version V1.0
	*/

	public void sendApplyLeagueMes(WXUserSS applyUser,WXUser creator,String name,String url){
		try {
			// 模板消息ID(“申请结果通知”，模板消息标题)
			String mbID = "BpOAs9k-LuhjfWHMKGRuxpC6fC4SWMP5XoPo-XNtWPM";
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String date = format.format(new Date());
			// 模板消息内容（视具体模板消息而定）
			String first = "";//标题
			String keyword1 = applyUser.getNickname();
			String keyword2 = "申请加入裁判组";
			String keyword3 = date;
			String remark = "联赛名称：" + name;
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
				String jsoninfo = "{\"template_id\": \"" + mbID
						+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + creator.getOpenId()
						+ "\",\"url\": \"" + url + "\", " + "\"data\":"
						+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
						+ "\" },"
						+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
						+ keyword1 + "\"},"
						+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
						+ keyword2 + "\"},"
						+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
						+ keyword3 + "\"},"
						+ "\"remark\": {\"color\": \"black\",\"value\": \""
						+ remark + "\" }}}";
				
				HttpClientUtils.postJSON(requestUrl, jsoninfo);
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* 描述: 同意加入联赛裁判组通过模板消息<br/>
	* @param applyUser 被邀请人昵称
	* @param leagueName 联赛名称
	* @param creator 联赛创建人
	* @param action 需要跳转的url链接，http开头的全路径
	* 创建时间：2018-3-14
	* @author  liudongxin
	* @version V1.0
	 */
	public void sendAgreeMsg(User applyUser,String leagueName,WXUserSS creator,String url){
		try {
			// 模板消息ID(“申请加入联赛成功”，模板消息标题)
			String mbID = "O_XXhWP10hZqUh9-puLqG9JN6p5xIwoitaq2WT_yuJg";
			// 用户的openID
			String openID = applyUser.getOpenId();
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String first = "";
			String date = format.format(new Date());
			String keyword1 = creator.getNickname() + "同意您加入裁判组";
			String keyword2 = date;
			String remark = "联赛名称：" + leagueName;
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			String jsoninfo = "{\"template_id\": \"" + mbID
					+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID
					+ "\",\"url\": \"" + url + "\", " + "\"data\":"
					+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
					+ "\" },"
					+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
					+ keyword1 + "\"},"
					+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
					+ keyword2 + "\"},"
					+ "\"remark\": {\"color\": \"black\",\"value\": \""
					+ remark + "\" }}}";
			
			HttpClientUtils.postJSON(requestUrl, jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* 描述: 拒绝或忽略邀请加入联赛向申请人发送模板消息<br/>
	* @param result 申请请加入联赛处理结果
	* @param creator 联赛创建人
	* @param applyUser 申请人
	* @param action 需要跳转的url链接，http开头的全路径
	* 创建时间：2018-3-15
	* @author liudongxin 
	* @version V1.0
	*/
	public void senLeagueRefMes(String result,WXUserSS creator,User applyUser,League league,String url){
		try {
			// 模板消息ID(“申请审批提醒”，模板消息标题)
			String mbID = "MuZrNtpsIKbsYYWUsd5YynVCL4h-t-VBdGNNwcDeHc4";
			// 用户的openID
			String openID = applyUser.getOpenId();
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String date = format.format(new Date());
			// 模板消息内容（视具体模板消息而定）
			String first = "";//标题
			if(result == null) result = "已忽略您的请求";
			String keyword1 = result;
			String keyword2 = date;
			String remark = "联赛名称："+league.getName();
			// 请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			String jsoninfo = "{\"template_id\": \"" + mbID
					+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openID
					+ "\",\"url\": \"" + url + "\", " + "\"data\":"
					+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
					+ "\" },"
					+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
					+ keyword1 + "\"},"
					+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
					+ keyword2 + "\"},"
					+ "\"remark\": {\"color\": \"black\",\"value\": \""
					+ remark + "\" }}}";
			
			HttpClientUtils.postJSON(requestUrl, jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	* 向裁判发比分申请<br/>
	* 创建时间：2018-3-16
	* @author liyupeng 
	* @version V1.0
	 */
	public void sendMatchExamineMessage(String requestUrl,String openId,String type,
			String name,String teamOne,String teamTwo,String  info,String content,String url)
	{
		try {
			// 模板消息ID(“消息发送状态提醒”，模板消息标题)
			String mbID = "tKfP_glk7Kfw52MDnxTpBTXRYpqcXg8mgRLWUsIKiIY";
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String first = "";//标题
			String keyword1 = format.format(new Date());
			String keyword2 = "无";
			String keyword3 = teamOne + " VS " + teamTwo;
			String remark = "";
				   remark = "联赛名称：" + name + "\\n轮次/场次：" + info;
				String jsoninfo = "{\"template_id\": \"" + mbID
						+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openId
						+ "\",\"url\": \"" + url + "\", " + "\"data\":"
						+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
						+ "\" },"
						+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
						+ keyword1 + "\"},"
						+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
						+ keyword2 + "\"},"
						+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
						+ keyword3 + "\"},"
						+ "\"remark\": {\"color\": \"black\",\"value\": \""
						+ remark + "\" }}}";
				
				HttpClientUtils.postJSON(requestUrl, jsoninfo);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	* 裁判比分审核结果<br/>
	* 创建时间：2018-3-16
	* @author liudongxin 
	* @version V1.0
	 */
	public void sendExamineMessage(String requestUrl,String openId,String type,
			String name,String teamOne,String teamTwo,String  info,String content,String url)
	{
		try {
			// 模板消息ID(“消息发送状态提醒”，模板消息标题)
			String mbID = "B58JohPbBxNpC0rAJQGimYNoGSo_Bc4nS-Jxc5xuBMg";
			// 模板消息内容（视具体模板消息而定）
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String first = "";//标题
			String keyword1 = type;
			String keyword2 = name + "  轮次/场次:" + info;
			String keyword3 = format.format(new Date());
			String keyword4 = content;
			String remark = "";
				String jsoninfo = "{\"template_id\": \"" + mbID
						+ "\",\"topcolor\": \"#FF0000\",\"touser\": \"" + openId
						+ "\",\"url\": \"" + url + "\", " + "\"data\":"
						+ "{\"first\": {\"color\": \"#173177\",\"value\": \"" + first
						+ "\" },"
						+ " \"keyword1\": {\"color\": \"#173177\",\"value\": \""
						+ keyword1 + "\"},"
						+ " \"keyword2\": {\"color\": \"#173177\",\"value\": \""
						+ keyword2 + "\"},"
						+ " \"keyword3\": {\"color\": \"#173177\",\"value\": \""
						+ keyword3 + "\"},"
						+ " \"keyword4\": {\"color\": \"#173177\",\"value\": \""
						+ keyword4 + "\"},"
						+ "\"remark\": {\"color\": \"#173177\",\"value\": \""
						+ remark + "\" }}}";
				
				HttpClientUtils.postJSON(requestUrl, jsoninfo);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
