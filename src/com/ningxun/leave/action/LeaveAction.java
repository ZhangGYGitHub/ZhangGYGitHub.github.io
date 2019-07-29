package com.ningxun.leave.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.leave.vo.Leave;
import com.ningxun.position.dao.PositionDao;
import com.ningxun.position.vo.Position;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.sign.dao.signDao;
import com.ningxun.sign.vo.Sign;
import com.ningxun.util.HtmlAjax;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: LeaveAction.java<br/>
 *<li>创建时间: 2017-7-26 下午3:01:08<br/>
 *
 *@author 高佳伟
 *@version [v1.00]
 */
public class LeaveAction extends BaseSupportAction {

	private Leave leave;//请假
	private Sign sign;
	private Integer id ;//请假id
	private Integer teamId;//球队id

	private Log log = LogFactory.getLog(getClass());//日志
	private signDao signDAO = new signDao();//签到报名
	private PositionDao positionDao = new PositionDao();//职位
	
	/**
	* 描述: 保存请假申请信息<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-26/下午4:34:41<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
	*/
	public void saveLeave() throws Exception{
		try {
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				HtmlAjax.getJson(getResponse(), null);//失败
				return;
			}
			// 添加请假数据
			leave.setDelState(0);
			leave.setCreateTime(new Date());
			leave.setCreateUser(wxUserSS.getId());
			
			Integer saveId = (Integer) HibernateDAO.save(leave);
			
			if (saveId != null) {//请假成功
				
				sign = signDAO.findSignUpList(leave.getRaceOrTrainId(),wxUserSS.getId(),leave.getRaceOrTrain());
				if (sign != null && sign.getSignUp()!=null && sign.getSignUp()==1) {//是否已报名
					sign.setSignUp(0);//修改报名状态
					HibernateDAO.update(sign);
					HtmlAjax.getJson(getResponse(), 2);//已报名，又请假
				}else {
					HtmlAjax.getJson(getResponse(), 1);//成功请假
				}
				//记录日志
				String content = "添加-编号为：\"" + leave.getId() + "\"的请假信息";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "添加");
				
			}else{
				//请假失败
				HtmlAjax.getJson(getResponse(), null);
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	} 
	
	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}

}
