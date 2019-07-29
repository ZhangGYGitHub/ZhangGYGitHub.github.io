package com.ningxun.wxuserinfo.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.BaseSupportAction;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.wxuserinfo.dao.MyCardDAO;
import com.opensymphony.xwork2.ActionContext;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: MyCardAction.java<br/>
 *<li>创建时间: 2017-7-24 上午10:57:50<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
@SuppressWarnings("serial")
public class MyCardAction extends BaseSupportAction {

	// 用于记录日志
	private Log log = LogFactory.getLog(getClass());
	// 用于查询
	private MyCardDAO myCardDAO = new MyCardDAO();
	// 队伍id
	private Integer teamId;
	// 成员id
	private Integer userId;
	/**
	 * 
	* 描述: 展示我的卡片<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-24/上午10:59:24<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public String showMyCard() {
		try {
			if (teamId != null && userId != null) {
				Object[] myCard = myCardDAO.findMyCard(teamId, userId);
				ActionContext.getContext().getValueStack().set("myCard", myCard);
				// 获取当前登录用户
				WXUserSS wxUserSS = (WXUserSS) getUser();
				ActionContext.getContext().getValueStack().set("currentUserId", wxUserSS.getId());
			}
			return SUCCESS;
		} catch (Exception e) {
			// 记录错误日志
			log.error(e);
			return ERROR;
		}
	}
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
