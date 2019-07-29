package com.ningxun.race.action;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.leave.dao.LeaveDAO;
import com.ningxun.leave.vo.Leave;
import com.ningxun.notice.action.NoticeAction;
import com.ningxun.position.dao.PositionDao;
import com.ningxun.position.vo.Position;
import com.ningxun.race.dao.RaceDAO;
import com.ningxun.race.vo.Race;
import com.ningxun.race.vo.RaceCustom;
import com.ningxun.score.dao.ScoreDao;
import com.ningxun.score.vo.Racescore;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.sign.dao.signDao;
import com.ningxun.sign.vo.Sign;
import com.ningxun.team.dao.TeaminfoDao;
import com.ningxun.team.vo.Teaminfo;
import com.ningxun.team.vo.UserTeam;
import com.ningxun.util.HtmlAjax;
import com.ningxun.util.ValidateUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: RaceAction.java<br/>
 *<li>创建时间: 2017-7-20 上午9:34:25<br/>
 *
 *@author 高佳伟
 *@version [v1.00]
 */
public class RaceAction extends BaseSupportAction {
	
	
	private List<Object[]> raceList;//比赛列表对象集合
	private List<UserTeam> userList;//用户球队关联列表集合
	private List<Object[]> leaveList;//请假列表集合
	private List signUpList;//报名列表集合
	private List signlist;//签到人员列表
	private List<Object> scoreMemberList;//进球人员列表
	private List<Object> cardMemberList;//得牌人员列表
	private List<Object[]> signList;//签到列表
	private List<Racescore> scoreList;//签到列表
	private List<Object[]> unSignList;//未到场列表
	private Object[] list;//详情展示
	
	private Race race;//比赛
	
	private Sign sign;//签到报名
	private Leave leave;//请假
	private Integer id;//比赛id
	private Integer teamId;//球队id
	private String teamName;//球队名称
	private Integer lid;//联赛名称
	private Integer myScore;//我方得分
	private Integer opScore;//对手得分
	private String picker1;//开始时间
	private String picker2;//结束时间
	private String league;//联赛
	private int raceState;//比赛开始状态：1未开始 2进行中 3已结束

	private Log log = LogFactory.getLog(getClass());//日志
	private HtmlAjax htmlAjax = new HtmlAjax();//ajax
	private RaceDAO raceDAO = new RaceDAO();//比赛
	private LeaveDAO leaveDAO = new LeaveDAO();//请假
	private signDao signDAO = new signDao();//签到报名
	private ScoreDao scoreDao = new ScoreDao();//比分Dao
	private PositionDao positionDao = new PositionDao();//职位
	private TeaminfoDao teaminfoDao = new TeaminfoDao();//球队
	
	/**
	 * 比赛列表
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String showRaceList() throws Exception{
		try {
			//当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			if (wxUserSS == null) {
				//没有登录
				addActionMessage("对不起，没有登录不能查看比赛！");
				return ERROR;
			}
			ActionContext.getContext().put("currentUserId", wxUserSS.getId());
			
			List<Object[]> list = raceDAO.findRaceListByTid(teamId,pageNo,PAGE_SIZE);
			List<RaceCustom> races = new ArrayList<RaceCustom>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] temp = list.get(i);
					//r.id,r.raceName,r.place,r.startTime,r.endTime,r.signType,r.signTime,r.isRelease,r.createUser,r.tid,number
					RaceCustom rc = new RaceCustom();
					rc.setId((Integer) temp[0]);
					rc.setRaceName(temp[1]+"");
					rc.setPlace(temp[2]+"");
					rc.setStartTime((Date) temp[3]);
					rc.setEndTime((Date) temp[4]);
					if (temp[5] != null ) {
						rc.setSignType((Integer) temp[5]);
					}
					if (temp[6] != null ) {
						rc.setSignTime((Integer) temp[6]);
					}
					rc.setIsRelease((Integer) temp[7]);
					rc.setCreateUser((Integer) temp[8]);
					rc.setTid((Integer) temp[9]);
					rc.setMyScore((Integer) temp[10]);
					rc.setOpScore((Integer) temp[11]);
					rc.setNumber(((BigInteger) temp[12]).intValue());
					//添加到集合
					races.add(rc);
				}
				ActionContext.getContext().put("races", races);
			}
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			ActionContext.getContext().put("position", position);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 上拉加载更多比赛列表
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	public void ajaxLoadMoreRaceList() throws Exception {
		try {
			String sql = raceDAO.getRaceListSql(teamId);
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, raceDAO.getTagName(), pageNo, 0);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	* 描述: 添加或编辑比赛<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-20/上午11:22:07<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public String toEdit(){
		try {
			//获取当前用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			}else if (position.getId() == 4) {//若职位为队员则被降职
				addActionMessage("您无权进行此操作！");
				return INPUT;
			}
			if (id != null) {
				race = (Race) HibernateDAO.findById(Race.class, id);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	

	/**
	* 描述: 详情展示<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-20/上午11:58:16<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public String showDetails(){
		try {
			if(id != null){
				Race race = raceDAO.finddetail(id).get(0);
				ActionContext.getContext().put("race", race);
				Teaminfo t = raceDAO.finddetail1(id).get(0);
				ActionContext.getContext().put("t", t);
				Teaminfo t2 = raceDAO.finddetail2(id).get(0);
				ActionContext.getContext().put("t2", t2);
		//	race = (Race) HibernateDAO.findById(Race.class,id);
			scoreMemberList = scoreDao.findGetScoreMemberList(id, teamId);//进球人员列表
			}
			//获取当前用户信息
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			ActionContext.getContext().put("position", position);
			
			race = (Race) HibernateDAO.findById(Race.class, id);//比赛详细
			if (race.getSignType() == 1) {
				signList =  signDAO.findSYsignList(id, teamId, 2);//签到列表
			}else {
				signList = signDAO.findTSignUserList(id, teamId, 2);//所有队员列表
			}
			scoreList = scoreDao.findRaceScoreList(id,teamId);//得分列表
			
			//
			//比赛开始时间
			Date startTime = race.getStartTime();
			//比赛结束时间
			Date endTime = race.getEndTime();
			//当前时间
			Date currentTime = new Date();
			
			//比赛状态  1未开始 2进行中  3已结束
			if (currentTime.getTime() > endTime.getTime()) {
				//已经结束
				raceState = 3;
			} else if ((currentTime.getTime() >= startTime.getTime()) && (currentTime.getTime() <= endTime.getTime())) {
				//进行中
				raceState = 2;
			} else {
				//未开始
				raceState = 1;
			}
			
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	* 描述: 模板消息跳转详情页<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-20/上午11:58:16<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public String showMesDetails(){
		try {
			if(id != null){
				race = (Race) HibernateDAO.findById(Race.class,id);
				if (race != null) {
					return SUCCESS;
				}
			}
			return ERROR;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	* 描述: 保存<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-20/上午11:24:47<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
	*/
	public void saveRace() throws Exception{
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
			//格式化页面传过来的时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(picker1 != null && picker1.length() >0){
				race.setStartTime(sdf.parse(picker1));
			}
			if(picker2 != null && picker2.length() >0){
				race.setEndTime(sdf.parse(picker2));
			}
			if (league != null && league.trim().length() != 0) {
				String leagues[] = league.split("（");
				race.setLeague(leagues[0]);
			}
			if (race.getId()==null) {
				//添加
				race.setCreateTime(new Date());
				race.setCreateUser(wxUserSS.getId());
				race.setTid(teamId);
				race.setDelState(0);
				race.setMyScore(0);
				race.setOpScore(0);
				Integer saveId = (Integer) HibernateDAO.save(race);
				if (saveId ==null) {
					HtmlAjax.getJson(getResponse(), false);//失败
					return;
				}
			
				
				userList = raceDAO.findUserList(teamId);
				//循环添加签到表数据
				StringBuilder sb = new StringBuilder();
				//insert into ticketdetail (ticketId,ticketNumber) values (1,1),(1,2),(1,3),(1,4) 批量插入格式
				sb.append("INSERT INTO sign (isAttendance, signUp, tid, uid, raceortrain, raceortrainId, createUser, createTime, delState) VALUES ");
				//转换时间为字符串格式
				//String date = DateTools.getDateTimeStr();
				//拼接sql
				Date creatTimeDate = new Date();
				if (race.getSignTime() != null && race.getSignTime() == 0) {
					creatTimeDate = new Date(race.getStartTime().getTime()-30*60*1000);
				}else if (race.getSignTime() != null && race.getSignTime() == 1) {
					creatTimeDate = new Date(race.getStartTime().getTime()-60*60*1000);
				}else if (race.getSignTime() != null && race.getSignTime() == 2) {
					creatTimeDate = new Date(race.getStartTime().getTime()-120*60*1000);
				}
				for (int i = 0; i < userList.size(); i++) {
					sb.append("('0' ,");//是否签到
					sb.append("0 ,");//是否签到
					sb.append(teamId+" ,");//球队id
					sb.append(userList.get(i).getUid()+" ,");//用户id
					sb.append("2 ,");//类型设为比赛
					sb.append(race.getId()+" ,");//比赛id
					sb.append(wxUserSS.getId() +" , '");// 创建人Id
					sb.append(sdf.format(creatTimeDate));// 签到创建时间为比赛开赛时间，便于统计
					sb.append("' ,");
					sb.append("0");// 删除状态
					sb.append(")");
					if (i < userList.size()-1 ) {
						sb.append(",");
					}
				}
				//返回-1为失败
				if (HibernateDAO.executeBySql(sb.toString()) == -1) {
					HtmlAjax.getJson(getResponse(), false);//失败
					return;
				}
				
			
				/*//循环添加比赛结果记录
				StringBuilder sbd = new StringBuilder();
				//insert into ticketdetail (ticketId,ticketNumber) values (1,1),(1,2),(1,3),(1,4) 批量插入格式
				sbd.append("INSERT INTO racescore (raceId, tid, uid, createUser, createTime, delState) VALUES ");
				//拼接sql
				for (int i = 0; i < userList.size(); i++) {
					sbd.append("("+race.getId()+" ,");//比赛id
					sbd.append(teamId+" ,");//球队id
					sbd.append(userList.get(i).getUid()+" ,");//用户id
					sbd.append(wxUserSS.getId() +" , '");// 创建人Id
					sbd.append(date);// 获取当前时间
					sbd.append("' ,");
					sbd.append("0 )");// 删除状态
					if (i < userList.size()-1 ) {
						sb.append(",");
					}
				}
				//返回-1为失败
				if (HibernateDAO.executeBySql(sb.toString()) == -1) {
					HtmlAjax.getJson(getResponse(), false);//失败
					return;
				}*/
				HtmlAjax.getJson(getResponse(), true);//成功
				//记录日志
				String content = "添加-编号为：\"" + race.getId() + "\"的比赛信息";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "添加");
			}else{
				//修改
				race.setModifyTime(new Date());
				race.setModifyUser(wxUserSS.getId());
				HibernateDAO.update(race);
				HtmlAjax.getJson(getResponse(), true);//成功
				//记录日志
				String content = "修改-编号为：\"" + race.getId() + "\"的比赛信息";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "修改");
				}
			if(race.getIsRelease()==1){
				id = race.getId(); 
				this.release();
			}
			} catch (Exception e) {
				log.error(e);// 记录错误日志
				e.printStackTrace();
				HtmlAjax.getJson(getResponse(), false);//失败
			}
	}
	
	/**
	* 描述: 删除<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-20/上午11:25:08<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public String Delete(){
		try {
			//获取当前登录人
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//查询被删除对象
			race = (Race) HibernateDAO.findById(Race.class, id);
			//添加删除信息
			race.setDeleteTime(new Date());
			race.setDeleteUser(wxUserSS.getId());
			race.setDelState(1);
			HibernateDAO.update(race);
			//记录日志
			String content = "删除-编号为：\"" + id + "\"的比赛信息";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "删除");
			//记录日志
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	* 描述: 报名<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-24/下午5:52:10<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public void signUp() throws Exception{
		try {
			//获取当前用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				HtmlAjax.getJson(getResponse(), 2);//失败
				return;
			}
			//ajax检验是否比赛逾期
			boolean flag = raceDAO.checkTimeOutByRid(id);
			if(!flag){
				//比赛未结束
				sign = signDAO.findSignUpList(id,wxUserSS.getId(),2);
				if(sign != null && sign.getSignUp() != null && sign.getSignUp() == 1){
					HtmlAjax.getJson(getResponse(), 3);//已经报名
					return;
				}	
				sign.setSignUp(1);
				HibernateDAO.update(sign);
				
				//修改请假状态
				leave = leaveDAO.findLeaveList(id, wxUserSS.getId(), 2);
				if (leave != null) {//此人已请过假
					HibernateDAO.delete(leave);
				}
				HtmlAjax.getJson(getResponse(), 1);//成功
				
				//记录日志
				String content = "报名-编号为：\"" + id + "\"的比赛";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "报名");
			} else {
				HtmlAjax.getJson(getResponse(), 4);//已经结束
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), 2);//失败
		}
	}

	/**
	* 描述: 点击发布<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-26/上午9:03:14<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
	*/
	public String release(){
		try {
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			}else if (position.getId() == 4) {//若职位为队员则被降职
				addActionMessage("您无权进行此操作！");
				return INPUT;
			}
			//发布模板消息
			NoticeAction action = new NoticeAction();
			action.saveRaceOrTrain(id, teamId, wxUserSS.getNickname(), 1);
			race = (Race) HibernateDAO.findById(Race.class, id);
			//未发布过，更改发布状态
			if(race.getIsRelease()!=1){
				race.setIsRelease(1);
				HibernateDAO.update(race);
				//记录日志
				String content = "发布-编号为：\"" + id + "\"的比赛信息";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "发布");
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	} 
	
	/**
	* 描述: 请假超时判断<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-26/下午7:32:45<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public String leave() throws Exception{
		try {
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				HtmlAjax.getJson(getResponse(),2);//异常
				return NONE;
			}
			//ajax检验是否比赛逾期
			boolean flag = raceDAO.checkTimeOutByRid(id);
			if(!flag){
				//比赛未结束
				leave = leaveDAO.findLeaveList(id,wxUserSS.getId(),2);
				if(leave != null){
					HtmlAjax.getJson(getResponse(),3);//已经请过假
				}else{
					HtmlAjax.getJson(getResponse(),1);//可以请假
				}
				return NONE;
			}else {
				HtmlAjax.getJson(getResponse(),4);//比赛已结束
				return NONE;
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(),2);//异常
			return NONE;
		}
	}
	
	/**
	 * 描述: 判断是否超时<br/>
	 *
	 * @return String 返回类型
	 *
	 * 创建时间：2017-7-26/下午7:32:45<br/>
	 * @author 高佳伟  
	 * @version V1.0  
	 */
	public void checkTimeOut() throws Exception{
		try {
			boolean flag = raceDAO.checkTimeOutByRid(id);
			HtmlAjax.getJson(getResponse(), flag);
		} catch (Exception e) {
			HtmlAjax.getJson(getResponse(), true);
		}
	}
	
	/**
	* 描述: 录入结果<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-27/下午6:33:57<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
	*/
	public String scoreAdd() throws Exception{
		try {
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				HtmlAjax.getJson(getResponse(), false);//失败
			}else if (position.getId() == 4) {//若职位为队员则被降职
				HtmlAjax.getJson(getResponse(), false);//失败
			}
			if(id != null){
				race = (Race) HibernateDAO.findById(Race.class, id);
				if (myScore == null || opScore == null) {
					HtmlAjax.getJson(getResponse(),0);//失败
				}
				race.setMyScore(myScore);
				race.setOpScore(opScore);
				if(myScore!=null && opScore!=null){
				HtmlAjax.getJson(getResponse(), true);//录入结果成功
				HibernateDAO.update(race);
				//记录日志
				String content = "录入-编号为：\"" + id + "\"的比赛结果";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "录入结果");
				}else{
				HtmlAjax.getJson(getResponse(), false);//失败
				}
			}
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			HtmlAjax.getJson(getResponse(), false);//失败
			return NONE;
		}
	}

	/**
	 * 
	* 描述: ajax查询队伍信息<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月26日/下午6:39:57<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void searchTeam() throws Exception{
		try {
			if (lid == null && teamName != null && teamName.trim().length() > 0) {
				//非联赛,球队名称不为空
				//后置模糊查询球队列表
				List<Object[]> list = teaminfoDao.queryTeaminfoListR(teamName,teamId);
				HtmlAjax.getJson(getResponse(), list);
			}else if (lid != null) {
				//联赛
				List<Object> list = new ArrayList<Object>();
				if (teamName != null && teamName.trim().length() > 0) {
					//将查询的球队放到最前面
					//根据球队名称查询的球队列表
					List<Object[]> queryTeamNameList = raceDAO.findTeamNameList(lid,teamName,teamId);
					list.addAll(queryTeamNameList);
					//查询全部，排出上面的列表
					List<Object[]> queryExcludeTeamNameList = raceDAO.findExcludeTeamNameList(lid,teamName,teamId);
					list.addAll(queryExcludeTeamNameList);
				} else {
					//查询全部
					List<Object[]> tempList = raceDAO.findAllLeagueTeamList(lid,teamId);
					list.addAll(tempList);
				}
				HtmlAjax.getJson(getResponse(), list);
			}else{
				//非联赛，球队名称为空
				HtmlAjax.getJson(getResponse(), null);
			}
		} catch (Exception e) {
			HtmlAjax.getJson(getResponse(), null);
			log.error(e);// 记录错误日志
			e.printStackTrace();
		}
	}

	/**
	* 描述: 展示参赛情况：报名列表，请假列表<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-29/上午11:37:49<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public String joinOn(){
		try {
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			}
			race = (Race) HibernateDAO.findById(Race.class, id);
			leaveList = leaveDAO.getLeaveList(id, 2,teamId);
			Integer leaveNum =0;
			for (int i = 0; i < leaveList.size(); i++) {
				if ((Integer)leaveList.get(i)[6] != null && (Integer)leaveList.get(i)[6] != 1 && (Integer)leaveList.get(i)[6] != 2) {
					leaveNum++;
				}
				
			}
			ActionContext.getContext().put("leaveNum", leaveNum);
			signUpList= signDAO.getSignUpList(id, 2,teamId);
			signlist = signDAO.findSYsignList(id, teamId, 2);
			unSignList = signDAO.findUnSignList(2, race.getId());
			Integer sizeNum =0;
			for (int i = 0; i < unSignList.size(); i++) {
				if ( unSignList.get(i)[4] == null) {
					sizeNum++;
				}
				
			}
			ActionContext.getContext().put("sizeNum", sizeNum);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	* 描述: ajax查询联赛列表<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-8-24/下午4:39:00<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public String leagueAjax(){
		try {
			List<Object[]> leagueAjax  = raceDAO.findLeagueListByTid(teamId);
			if(leagueAjax != null && leagueAjax.size()>0){
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray =new JSONArray();
				for (int i = 0; i < leagueAjax.size(); i++) {
					jsonObject.put("title",leagueAjax.get(i)[1].toString().trim()+"（创建人："+leagueAjax.get(i)[2].toString().trim()+"）");
					jsonObject.put("value",leagueAjax.get(i)[0]);
					jsonArray.add(jsonObject);
				}

				JSONObject object = new JSONObject();
				object.put("array", jsonArray);
				HtmlAjax.getJson(getResponse(), object);
			}else{
				HtmlAjax.getJson(getResponse(), null);
			}
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	* 描述: 后台校验<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-8/上午11:56:53<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public void validateRaceSave() {
		Boolean haveValidate = true;
		//比赛名称
		Object checkProperty = race.getRaceName();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "raceName", "比赛名称", 20, true)) {
			haveValidate = false;
		}
		
		//赛程类型
		checkProperty = race.getType();
		if (!ValidateUtil.submitCheck(this, "整数", checkProperty, "type", "赛程类型",11, true)) {
			haveValidate = false;
		}
		
		//开始时间
		checkProperty = race.getStartTime();
		if (!ValidateUtil.submitCheck(this, "时间", checkProperty, "startTime", "开始时间",0, true)) {
			haveValidate = false;
		}
		
		//结束时间
		checkProperty = race.getEndTime();
		if (!ValidateUtil.submitCheck(this, "时间", checkProperty, "endTime", "结束时间",0, true)) {
			haveValidate = false;
		}
		
		//比赛地点
		checkProperty = race.getPlace();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "place", "比赛地点", 20, true)) {
			haveValidate = false;
		}
		
		//资金耗费
		checkProperty = race.getWaste();
		if (!ValidateUtil.submitCheck(this, "小数", checkProperty, "waste", "资金耗费", 10, false)) {
			haveValidate = false;
		}
		
		//备注
		checkProperty = race.getRemark();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "remark", "备注", 200, false)) {
			haveValidate = false;
		}
		
		if (haveValidate) {
			return;
		}
		
	}
	
	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public List<Object[]> getRaceList() {
		return raceList;
	}


	public void setRaceList(List<Object[]> raceList) {
		this.raceList = raceList;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public Race getRace() {
		return race;
	}
	public void setRace(Race race) {
		this.race = race;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPicker2() {
		return picker2;
	}
	
	public void setPicker2(String picker2) {
		this.picker2 = picker2;
	}
	
	public String getPicker1() {
		return picker1;
	}
	
	public void setPicker1(String picker1) {
		this.picker1 = picker1;
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
	public List<UserTeam> getUserList() {
		return userList;
	}
	
	public void setUserList(List<UserTeam> userList) {
		this.userList = userList;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getMyScore() {
		return myScore;
	}

	public void setMyScore(Integer myScore) {
		this.myScore = myScore;
	}

	public Integer getOpScore() {
		return opScore;
	}

	public void setOpScore(Integer opScore) {
		this.opScore = opScore;
	}


	public List getSignUpList() {
		return signUpList;
	}

	public List<Object[]> getLeaveList() {
		return leaveList;
	}

	public void setLeaveList(List<Object[]> leaveList) {
		this.leaveList = leaveList;
	}

	public void setSignUpList(List signUpList) {
		this.signUpList = signUpList;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	public List getSignlist() {
		return signlist;
	}

	public void setSignlist(List signlist) {
		this.signlist = signlist;
	}
	public List<Object> getScoreMemberList() {
		return scoreMemberList;
	}
	
	public void setScoreMemberList(List<Object> scoreMemberList) {
		this.scoreMemberList = scoreMemberList;
	}
	
	public List<Object> getCardMemberList() {
		return cardMemberList;
	}
	
	public void setCardMemberList(List<Object> cardMemberList) {
		this.cardMemberList = cardMemberList;
	}

	public List<Object[]> getSignList() {
		return signList;
	}

	public void setSignList(List<Object[]> signList) {
		this.signList = signList;
	}

	public List<Racescore> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<Racescore> scoreList) {
		this.scoreList = scoreList;
	}

	public int getRaceState() {
		return raceState;
	}

	public void setRaceState(int raceState) {
		this.raceState = raceState;
	}

	public List<Object[]> getUnSignList() {
		return unSignList;
	}

	public void setUnSignList(List<Object[]> unSignList) {
		this.unSignList = unSignList;
	}

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}
}
