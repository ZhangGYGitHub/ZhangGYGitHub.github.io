package com.ningxun.score.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.position.dao.PositionDao;
import com.ningxun.position.vo.Position;
import com.ningxun.race.vo.Race;
import com.ningxun.score.dao.ScoreDao;
import com.ningxun.score.vo.Racescore;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.sign.dao.signDao;
import com.ningxun.sign.vo.Sign;
import com.ningxun.util.HtmlAjax;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: ScoreAction.java<br/>
 *<li>创建时间: 2017-8-20 下午3:17:37<br/>
 *
 *@author 高佳伟
 *@version [v1.00]
 */
public class ScoreAction extends BaseSupportAction {

	private Integer id;//id
	private Integer uid;//用户id
	private Integer raceId;//比赛id
	private Integer teamId;//球队id
	private Racescore racescore;//得分实例
	private Sign sign;//签到
	private Race race;
	
	private Integer getScore;//得失球
	private Integer yellowCard;//黄牌
	private Integer redCard;//红牌
	
	private List<Object[]> signList;//签到列表
	private List<Racescore> scoreList;//签到列表
	
	private signDao signDao = new signDao();//签到Dao
	private ScoreDao scoreDao = new ScoreDao();//签到Dao
	private PositionDao positionDao = new PositionDao();//职位Dao
	
	private Log log = LogFactory.getLog(getClass());//日志
 	
	/**
	* 描述: 添加比赛结果<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-8-21/上午9:29:39<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	@SuppressWarnings("unchecked")
	public String scoreAdd(){
		try {
			race = (Race) HibernateDAO.findById(Race.class,raceId);//比赛详细
			signList =  signDao.findSYsignList(raceId, teamId, 2);//签到列表
			scoreList = scoreDao.findRaceScoreList(raceId,teamId);//得分列表
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	* 描述: 保存比赛结果<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-8-21/上午9:30:25<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
	*/
	public void scoreSave() throws Exception {
		try {
			//获取当前用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				HtmlAjax.getJson(getResponse(), false);//失败
				return;
			}else if (position.getId() == 4) {//若职位为队员则被降职
				HtmlAjax.getJson(getResponse(), false);//失败
				return;
			}
			racescore = scoreDao.findScoreByUidTidRid(uid,raceId,teamId);
			//race = (Race) HibernateDAO.findById(Race.class, raceId);
			if (racescore == null) {//添加
				HtmlAjax.getJson(getResponse(), false);//失败
				return;
			}

			if (getScore != null && getScore == 1) {//得分
				getScore = racescore.getGetScore() + 1;
				racescore.setGetScore(getScore);//得失球
				//race.setMyScore(race.getMyScore()+1);//比赛结果+1
				HtmlAjax.getJson(getResponse(), uid);//成功
			}
			
			if (getScore != null && getScore == -1) {//得分
				getScore = racescore.getGetScore() - 1;
				racescore.setGetScore(getScore);//得失球
				//race.setMyScore(race.getMyScore()+1);//比赛结果+1
				HtmlAjax.getJson(getResponse(), uid);//成功
			}
			
			if (yellowCard != null && yellowCard == 1) {//得黄牌
				yellowCard = racescore.getYellowCard() + 1;
				racescore.setYellowCard(yellowCard);//黄牌
				HtmlAjax.getJson(getResponse(), uid);//成功
			}
			
			if (redCard != null && redCard == 1) {//得红牌
				redCard = racescore.getRedCard() + 1;
				racescore.setRedCard(redCard);//红牌
				HtmlAjax.getJson(getResponse(), uid);//成功
			}
			
			racescore.setModifyTime(new Date());//修改时间
			racescore.setModifyUser(wxUserSS.getId());//修改人
			HibernateDAO.update(racescore);
			// 操作日志
			String content = "保存编号为：\"" + raceId + "\"的比赛结果";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "保存比赛结果");
			
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), false);//失败
		}
	}

	public Integer getRaceId() {
		return raceId;
	}

	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Racescore getRacescore() {
		return racescore;
	}

	public void setRacescore(Racescore racescore) {
		this.racescore = racescore;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}

	public List<Object[]> getSignList() {
		return signList;
	}

	public void setSignList(List<Object[]> signList) {
		this.signList = signList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getGetScore() {
		return getScore;
	}

	public void setGetScore(Integer getScore) {
		this.getScore = getScore;
	}

	public Integer getYellowCard() {
		return yellowCard;
	}

	public void setYellowCard(Integer yellowCard) {
		this.yellowCard = yellowCard;
	}

	public Integer getRedCard() {
		return redCard;
	}

	public void setRedCard(Integer redCard) {
		this.redCard = redCard;
	}

	public List<Racescore> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<Racescore> scoreList) {
		this.scoreList = scoreList;
	}
	
	public Race getRace() {
		return race;
	}
	
	public void setRace(Race race) {
		this.race = race;
	}
}

