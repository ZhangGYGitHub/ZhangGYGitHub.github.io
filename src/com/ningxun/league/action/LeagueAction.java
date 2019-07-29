package com.ningxun.league.action;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.beans.editors.IntEditor;

import com.ningxun.apply.dao.ApplyDao;
import com.ningxun.apply.vo.ApplyLeague;
import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.league.dao.LeagueDAO;
import com.ningxun.league.vo.League;
import com.ningxun.league.vo.LeagueCard;
import com.ningxun.league.vo.LeagueMatch;
import com.ningxun.league.vo.LeagueScore;
import com.ningxun.league.vo.LeagueSoccer;
import com.ningxun.league.vo.MatchExamine;
import com.ningxun.league.vo.User;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.team.vo.Teaminfo;
import com.ningxun.tools.Config;
import com.ningxun.tools.ShareUtil;
import com.ningxun.tools.TemplateMessage;
import com.ningxun.tools.TokenThread;
import com.ningxun.util.HtmlAjax;
import com.ningxun.wxuserinfo.dao.LocationDAO;
import com.ningxun.wxuserinfo.vo.Location;
import com.ningxun.wxuserinfo.vo.WXUser;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class LeagueAction extends BaseSupportAction{
	
	private static Integer id;
	private League league;
	private Integer turn;
	private Integer lmid;
	private LeagueMatch match;
	private String t1;
	private String t2;
	private Integer isExamine;//0审核 1查看
	
	private List<League> myLeagues;
	private List<League> joinLeagues;
	private List<LeagueMatch> turns;
	private List<LeagueMatch> scheduleList;
	private List<Object[]> scoreList;
	private List<Object[]> shootList;
	private List<Object[]> matchs;
	private List<Object[]> leagueScore;
	private List<Teaminfo> teamList;
	private List<Object[]> oneExamine;
	private List<Object[]> twoExamine;
	private List<Object[]> leagueRefereeList;
	private List<Object[]> redList;
	private List<Object[]> yellowList;
	
	private String keyword;
	private Integer uid;
	private ApplyDao applyDao = new ApplyDao();
	private ApplyLeague referees;
	private Log log = LogFactory.getLog(getClass());
	private LeagueDAO leagueDAO = new LeagueDAO();
	private List<Object[]> referee;

	/**
	 * 
	* @Description: 联赛列表展示
	* @author liyvpeng  
	* @date 2017-12-18
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String leagueList(){
		try {
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//我创建的联赛
			myLeagues = leagueDAO.findLeagueByUserId(wxUserSS.getId());
			//我加入的联赛(按球队和裁判选)
			joinLeagues = leagueDAO.findMyJoinLeague(wxUserSS.getId());
			//比分编辑(加入比赛的人均可见)
			leagueScore = leagueDAO.findReferee(joinLeagues, wxUserSS.getId());
		return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 
	* @Description: 添加联赛
	* @author liyvpeng  
	* @date 2018-3-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String leagueAdd() {
		id = null;
		return SUCCESS;
	}
	/**
	 * 
	* @Description: 保存联赛基本信息
	* @author liyvpeng  
	* @date 2018-3-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void leagueSave(){
		try {
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//add
			if(id == null){
				if (upload != null || upload.get(0) != null) {
					int saveFile = saveFile(Config.getFileUploadPath());
					if(saveFile == 0){
						String path=uploadFiles.get(0).getUploadFileName();
						String path1=uploadFiles.get(0).getUploadRealName();
						league.setAttachmentOldName(path);
						league.setAttachmentNewName(path1);
					}
				}
				league.setCreatorId(wxUserSS.getId());
				league.setCreateTime(new Date());
				league.setTeamNum(0);
				league.setDelState(0);
				HibernateDAO.save(league);
				String content = "添加-编号为：\"" + league.getId() + "\"名称为：\"" + league.getName() +"\"的联赛";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "联赛");
				HtmlAjax.getJson(getResponse(), true);
			}
			//edit
			if (upload != null && upload.get(0) != null) {
				int saveFile = saveFile(Config.getFileUploadPath());
				if(saveFile == 0){
					new File(Config.getFileUploadPath() + league.getAttachmentNewName()).delete();
					String path=uploadFiles.get(0).getUploadFileName();
					String path1=uploadFiles.get(0).getUploadRealName();
					league.setAttachmentOldName(path);
					league.setAttachmentNewName(path1);
				}
			}
			league.setModifier(wxUserSS.getId());
			league.setModifyTime(new Date());
			HibernateDAO.update(league);
			String content = "修改-编号为：\"" + league.getId() + "\"名称为：\"" + league.getName() +"\"的联赛";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "联赛");
			HtmlAjax.getJson(getResponse(), true);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
		}
	}
	
	/**
	 * 
	* @Description: 编辑联赛
	* @author liudongxin 
	* @date 2018-3-19
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String leagueEdit() {
		try {
			//当前登录人
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取基本信息
			league = leagueDAO.findLeagueById(id);
			//获取turn信息
			turns = leagueDAO.CountTurnById(id);
			ActionContext.getContext().put("currentUserId", loginUser.getId());
			//获取待处理的消息数
			List<WXUser> waitLeagueHandleList = applyDao.getApplyLeagueWaitHandleList(id);
			ActionContext.getContext().put("waitLeagueHandleList", waitLeagueHandleList);
			//已加入裁判组列表 (可以查到裁判id)
			List<Object[]> agreeList = applyDao.getAgreeHandleList(id);
			ActionContext.getContext().put("agreeList", agreeList);
			//携带分享的数据到页面
			String appId = Config.getAppid();
			//时间戳
			String timestamp = ShareUtil.getTimeStamp();
			//随机字符串
			String nonceStr = ShareUtil.getNonceStr();
			//请求路径
			String requestURL = getRequest().getRequestURL().toString();
			//拼接完整url
			String url = requestURL+"?id="+league.getId();
			//签名
			String signature = ShareUtil.getSignature(nonceStr,timestamp,url);
			ActionContext.getContext().put("appId", appId);
			ActionContext.getContext().put("timestamp", timestamp);
			ActionContext.getContext().put("nonceStr", nonceStr);
			ActionContext.getContext().put("signature", signature);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
		}
		return SUCCESS;
	}
	
	
	/**
	 * 联赛分享链接
	 * @author liudongxin
	 * @date 2018年3月16日
	 * @version 1.0
	 * @throws Exception
	 */
	public String shareUrl() throws Exception{
		try {
			//如果没有登录跳转登录
			WXUserSS loginUser = (WXUserSS) getUser();
			if (loginUser == null){
				//没有登录
				return LOGIN;
			}
			//查看是否已经加入联赛裁判组了
			Object[] loginUserJoin = leagueDAO.queryJoinByApplyLeagueUid(league.getId(),loginUser.getId());
			if (loginUserJoin != null){
				//已经加入联赛
				return "toIndex";
			} 
			//登录后，直接跳转申请加入联赛页面
			//携带分享联赛的信息
			Object[] obj = leagueDAO.getLeagueinfoDetail(league.getId());
			ActionContext.getContext().put("obj", obj);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);// 记录错误日志
			return ERROR;
		}
	}
	
	/**
	 * 
	* 描述: 展示裁判个人信息<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2018-3-19
	* @author liudongxin  
	* @version V1.0
	 */
	public String showReferee() {
		try {
			Object[] obj = leagueDAO.findRefereeCard(uid,id);
			ActionContext.getContext().getValueStack().set("obj",obj);
			return SUCCESS;
		} catch (Exception e) {
			// 记录错误日志
			log.error(e);
			return ERROR;
		}
	}
	
	/**
	 * 
	* @Description: 射手榜个人信息
	* @author liyvpeng  
	* @date 2018-4-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String showSoccer() {
		try {
			Integer soccer = new Integer(getRequest().getParameter("soccer"));
			User u = (User) HibernateDAO.findById(User.class, soccer);
			Integer redNum = leagueDAO.countRedNumByUid(u.getId(), id);
			Integer yellowNum = leagueDAO.countRedNumByUid(u.getId(), id);
			List<Location> locations = new LocationDAO().findUserLocation(u.getId());
			String locationString = "";
			if (locations != null && locations.size() > 0) {
				for (int i = 0; i < locations.size(); i++) {
					
					if (i == locations.size() - 1) {
						locationString += locations.get(i).getLocationName();
					} else {
						locationString += locations.get(i).getLocationName()+ ",";
					}
				}
				ActionContext.getContext().getValueStack().set("myLocation", locationString);
			}
			ActionContext.getContext().getValueStack().set("u", u);
			ActionContext.getContext().getValueStack().set("redNum", redNum);
			ActionContext.getContext().getValueStack().set("yellowNum", yellowNum);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
		}
		return SUCCESS;
	}
	/**
	 * 
	* @Description: 删除裁判
	* @author liudongxin  
	* @date 2018-3-20
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String refereeDel(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			referees = leagueDAO.findRefereeById(uid);
			referees.setDeletor(loginUser.getId());
			referees.setDelTime(new Date());
			referees.setDelState(1);
			HibernateDAO.update(referees);
			String content = "删除-编号为：\"" + referees.getId() + "\"ID为：\"" + referees.getUid() +"\"的人员";
			AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "人员");
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 删除联赛
	* @author liyvpeng  
	* @date 2018-3-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String leagueDel(){
		try {
			WXUserSS wxUserSS = (WXUserSS) getUser();
			league = leagueDAO.findLeagueById(id);
			league.setDeletor(wxUserSS.getId());
			league.setDelTime(new Date());
			league.setDelState(1);
			HibernateDAO.update(league);
			String content = "删除-编号为：\"" + league.getId() + "\"名称为：\"" + league.getName() +"\"的联赛";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "联赛");
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
		}
		return SUCCESS;
	}
	/**
	 * 
	* @Description: 联赛排行榜
	* @author liyvpeng  
	* @date 2018-1-7
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String showBillboard() {
		try {
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ TokenThread.accessToken.getToken();
			
			System.out.println(requestUrl);
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//联赛
			league = leagueDAO.findLeagueById(id);
			//轮次展示
			turns = leagueDAO.CountTurnById(id);
			//积分榜
			List<LeagueScore> lss = leagueDAO.findScoreByLid(id);
			//积分计算
			int win = (int)league.getWinScore();
			int lose = (int)league.getLoseScore();
			int draw = (int)league.getDrawScore();
			
			for(int i = 0; i < lss.size(); i++){
				LeagueScore ls = lss.get(i);
				if(ls.getWin() == null) ls.setWin(0);
				if(ls.getDraw() == null) ls.setDraw(0);
				if(ls.getLose() == null) ls.setLose(0);
				ls.setScore(win * ls.getWin() + lose * ls.getLose() + draw * ls.getDraw());
				HibernateDAO.update(ls);
				String content = "修改-编号为：\"" + ls.getId() + "\"的积分榜";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "积分榜");
			}
			scoreList = leagueDAO.findScoreList(id);
			//射手榜
			shootList = leagueDAO.findShootList(id);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	* @Description: ajax获取Match
	* @author liyvpeng  
	* @date 2018-3-8
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void ajaxGetMatch() throws Exception {
		try {
			//寻找每场比赛
			matchs = leagueDAO.findMatchByTurn(id, turn);
			//添加是否为队长的判断 且是否审核中
			int userid = ((WXUserSS)getUser()).getId();
			int num = matchs.size();
			for(int i = 0; i < num ; i++){
				Object[] isCaptain = leagueDAO.isCaptainOfMatch((Integer) matchs.get(i)[11], userid);
				matchs.add(isCaptain);
			}
			HtmlAjax.getJson(getResponse(), matchs);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 
	* @Description: ajax校验联赛名称
	* @author liyvpeng  
	* @date 2018-3-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void ajaxValideLeagueName() throws Exception{
		try {
			//编辑时防止名称未改报错
			if(id != null)
				league = leagueDAO.findLeagueById(id);
			List<League> ls = leagueDAO.findLeagueNameByName(getRequest().getParameter("name"));
				if(ls.size() == 0 || (league != null && ls.get(0).getName().equals(league.getName()))){
					HtmlAjax.getJson(getResponse(), null);
				}else{
					HtmlAjax.getJson(getResponse(), ls);
				}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 
	* @Description: 附件下载
	* @author liyvpeng  
	* @date 2018-3-12
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String leagueFile(){
		try {	
			league = leagueDAO.findLeagueById(id);
		} catch (Exception e) {
				log.error(e);// 记录错误日志
				addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
				return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ajax加载场次
	* @author liyvpeng  
	* @date 2018-3-14
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void ajaxLoadSite() throws Exception {
		try {		
			List<Object[]> sites = leagueDAO.findMatchByTurn(id, turn);
			//将日期转为字符串
			for(int i = 0; i < sites.size(); i++){
				if(sites.get(i)[3] != null ){
					String tmStart = sites.get(i)[3].toString();
					String start = tmStart.substring(0, tmStart.length()-5);
					sites.get(i)[3] = start;
				}
				if(sites.get(i)[4] != null ){
					String tmEnd = sites.get(i)[4].toString();
					String end = tmEnd.substring(0, tmEnd.length()-5);
					sites.get(i)[4] = end;
				}
			}
			HtmlAjax.getJson(getResponse(), sites);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 
	* @Description: ajax加载Team
	* @author liyvpeng  
	* @date 2018-3-14
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void ajaxLoadTeam() throws Exception {
		String name = getRequest().getParameter("name");
		try {
			List<Object[]> list = leagueDAO.findTeamLikeName(name);
			HtmlAjax.getJson(getResponse(), list);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	
	/**
	 * 
	* @Description: ajax保存match(由于表单复杂性 以特殊方式保存：覆盖已有比赛方式)
	* @author liyvpeng  
	* @date 2018-3-15
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void ajaxSaveMatch() throws Exception {
		try {
			
			WXUserSS wxUserSS = (WXUserSS)getUser();
			String ajax = getRequest().getParameter("ajax");
			String[] delSiteList = getRequest().getParameter("delSiteList").split(",");
			String[] delTurnList = getRequest().getParameter("delTurnList").split(",");
			
			//分割字符串为一场场比赛
			String[] matchs = ajax.split(";");
			
			//lmid按场删除
			for(int i = 0; i < delSiteList.length; i++){
				if(!delSiteList[i].equals("") && !delSiteList[i].equals("0")){
					Integer lmid = new Integer(delSiteList[i]);
					LeagueMatch lm = (LeagueMatch) HibernateDAO.findById(LeagueMatch.class, lmid);
					lm.setDelState(1);
					lm.setDeletor(wxUserSS.getId());
					lm.setDelTime(new Date());
					HibernateDAO.update(lm);
					String content = "删除-编号为：\"" + lm.getId() + "\"的比赛";
					AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "比赛");
				}
			}
			//turn按轮删除
			for(int i = 0; i < delTurnList.length; i++){
				if(!delTurnList[i].equals("")){
					Integer turnid = new Integer(delTurnList[i]);
					List<LeagueMatch> lms = leagueDAO.findByTurntoDel(turnid, id);
					for(int j = 0; j < lms.size(); j++){
						LeagueMatch lm = lms.get(j);
						lm.setDelState(1);
						lm.setDeletor(wxUserSS.getId());
						lm.setDelTime(new Date());
						HibernateDAO.update(lm);
						String content = "删除-编号为：\"" + lm.getId() + "\"的比赛";
						AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "比赛");
					}
				}
			}
			
			//先删后改防止出错	
			for(int i = 0; i < matchs.length; i++){
				//比赛信息 0site 1turn 2lmid 3startTime 4endTime 5id1 6id2
				String[] info = matchs[i].split(",");
				//lmid = 0 添加 否则更改
				if(!info[2].equals("0")){
					LeagueMatch lm = (LeagueMatch) HibernateDAO.findById(LeagueMatch.class, new Integer(info[2]));
					//修改比赛
					if(!info[0].equals("")){
						lm.setSite(new Integer(info[0]));
					}else {
						lm.setSite(null);
					}
					if(!info[1].equals("")){
						lm.setTurn(new Integer(info[1]));
					}else {
						lm.setTurn(null);
					}
					if(!info[3].equals("")){
						lm.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(info[3]));
					}else {
						lm.setStartTime(null);
					}
					if(!info[4].equals("")){
						lm.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(info[4]));
					}else {
						lm.setEndTime(null);
					}
					if(!info[5].equals("0")){
						lm.setTeamOne(new Integer(info[5]));
					}else {
						lm.setTeamOne(null);
					}
					if(!info[6].equals("0")){
						lm.setTeamTwo(new Integer(info[6]));
					}else {
						lm.setTeamTwo(null);
					}
					lm.setModifier(wxUserSS.getId());
					lm.setModifyTime(new Date());
					HibernateDAO.update(lm);
					String content = "修改-编号为：\"" + lm.getId() + "\"的比赛";
					AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "比赛");
				}else{
					LeagueMatch lm = new LeagueMatch();
					//添加比赛
					if(!info[0].equals("")){
						lm.setSite(new Integer(info[0]));
					}else {
						lm.setSite(null);
					}
					if(!info[1].equals("")){
						lm.setTurn(new Integer(info[1]));
					}else {
						lm.setTurn(null);
					}
					if(!info[3].equals("")){
						lm.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(info[3]));
					}else {
						lm.setStartTime(null);
					}
					if(!info[4].equals("")){
						lm.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(info[4]));
					}else {
						lm.setEndTime(null);
					}
					if(!info[5].equals("0")){
						lm.setTeamOne(new Integer(info[5]));
					}else {
						lm.setTeamOne(null);
					}
					if(!info[6].equals("0")){
						lm.setTeamTwo(new Integer(info[6]));
					}else {
						lm.setTeamTwo(null);
					}
					lm.setLeagueId(id);
					lm.setFinish(0);
					lm.setCreator(wxUserSS.getId());
					lm.setCreateTime(new Date());
					lm.setDelState(0);
					HibernateDAO.save(lm);
					String content = "添加-编号为：\"" + lm.getId() + "\"的比赛";
					AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "比赛");
				}
			}
			//重新计算TeamNum
			int teamNum = leagueDAO.countLeagueTeamBylid(id);
			league = leagueDAO.findLeagueById(id);
			league.setTeamNum(teamNum);
			HibernateDAO.update(league);
			String content = "修改-编号为：\"" + id + "\"的联赛";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "联赛");
			//保存成功后返回
			HtmlAjax.getJson(getResponse(), true);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
		
	}
	/**
	 * 
	* @Description: 比分编辑(用来跳转添加比分页面)
	* @author liyvpeng  
	* @date 2018-3-17
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String leagueScore() throws Exception {
		try {
			turns = leagueDAO.CountTurnById(id);
			leagueRefereeList = leagueDAO.findRefereeByLid(id);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 赛情编辑
	* @author liyvpeng  
	* @date 2018-3-18
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String scoreEdit(){
		try {
			WXUserSS wxUserSS = (WXUserSS) getUser();
			t1 = getRequest().getParameter("t1");//队1 name
			t2 = getRequest().getParameter("t2");//队2 name
			uid = wxUserSS.getId();
			match = (LeagueMatch) HibernateDAO.findById(LeagueMatch.class, lmid);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ajax查找队员
	* @author liyvpeng  
	* @date 2018-3-18
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void ajaxFindMemberByTid() throws Exception {
		try {
			int tid = new Integer(getRequest().getParameter("tid"));
			List<Object[]> members = leagueDAO.findTeamMemberByTid(tid);
			HtmlAjax.getJson(getResponse(), members);
		}catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 
	* @Description: 提交比赛结果审核
	* @author liyvpeng  
	* @date 2018-3-20
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void subMatchToExam() throws Exception{
		try {
			
			LeagueMatch lm = (LeagueMatch) HibernateDAO.findById(LeagueMatch.class, lmid);
			//若未完成则提交
			if(lm.getFinish() == null || lm.getFinish() == 0){
				//[0]为tid 之后为球员id和进球数
				String t1[] = getRequest().getParameter("t1").split("#");
				String t2[] = getRequest().getParameter("t2").split("#");
				//红黄牌 [i]为sid [i+1]为牌数
				String red[] = getRequest().getParameter("red").split("#");
				String yellow[] = getRequest().getParameter("yellow").split("#");
				//提交红黄牌审核
				subCardNum(red, yellow);
				//提交to审核
				subToExamine(t1);
				subToExamine(t2);
				//发给裁判审核通知  [2]openId
				List<Object[]> resList = leagueDAO.findRefereeByLid(id);
				TemplateMessage tMessage = new TemplateMessage();
				//填写模板消息需要的信息(为了尽可能复用模板)
				league = leagueDAO.findLeagueById(id);
				String type = "比分审核申请";
				String name = league.getName();
				String teamOne = getRequest().getParameter("oneName");
				String teamTwo = getRequest().getParameter("twoName");
				String info = lm.getTurn() + "/" + lm.getSite();
				User user = (User) HibernateDAO.findById(User.class, uid);
				String content = "来自" + user.getNickname() + "的比分审核申请";
				String url = getRequest().getRequestURL().toString();
				//裁剪url至league/
				url = url.substring(0, url.length() - 14);
				String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_" +
						"token="
						+ TokenThread.accessToken.getToken();
				for(int i = 0; i < resList.size(); i++) {
					url += "scoreExamine?showType=1&isSubmit=1&isExamine=0&id=" + id + "&lmid=" + lmid + "&uid=" + getRequest().getParameter("uid");
					tMessage.sendMatchExamineMessage(requestUrl, resList.get(i)[2].toString(), type, name, teamOne, teamTwo, info, content, url);
				}
			}
			HtmlAjax.getJson(getResponse(), true);
		}catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 
	* @Description: 提交红黄牌审核 subMatchToExam的附属方法
	* @author liyvpeng  
	* @date 2018-4-11
	* @version V2.0
	* @return
	 * @throws NumberFormatException 
	* @throws Exception
	 */
	private void subCardNum(String[] red, String[] yellow) throws NumberFormatException, Exception {
		WXUserSS wxUserSS = (WXUserSS) getUser();
		//红牌
		for(int i = 0; i < red.length; i += 2){
			List<LeagueCard> list = leagueDAO.findLeagueCardsBySidAndlmid(new Integer(red[i]), lmid, wxUserSS.getId());
			//修改
			if(list.size() != 0){
				LeagueCard lc = list.get(0);
				lc.setRedNum(new Integer(red[i+1]));
				HibernateDAO.update(lc);
				String content = "添加-编号为：\"" + lc.getId() + "\"的红牌数";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "红牌数");
			}else {
				//添加
				LeagueCard lc = new LeagueCard();
				lc.setLid(id);
				lc.setLmid(lmid);
				lc.setSid(new Integer(red[i]));
				lc.setRedNum(new Integer(red[i+1]));
				lc.setYellowNum(0);
				lc.setSubmitter(wxUserSS.getId());
				lc.setSubTime(new Date());
				lc.setResult(0);
				HibernateDAO.save(lc);
				String content = "添加-编号为：\"" + lc.getId() + "\"的红牌数";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "红牌数");
			}

		}
		//黄牌
		for(int i = 0; i < yellow.length; i += 2){
			List<LeagueCard> list = leagueDAO.findLeagueCardsBySidAndlmid(new Integer(yellow[i]), lmid, wxUserSS.getId());
			//修改
			if(list.size() != 0){
				LeagueCard lc = list.get(0);
				lc.setYellowNum(new Integer(yellow[i+1]));
				HibernateDAO.update(lc);
				String content = "添加-编号为：\"" + lc.getId() + "\"的红牌数";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "红牌数");
			}else {
				//添加
				LeagueCard lc = new LeagueCard();
				lc.setLid(id);
				lc.setLmid(lmid);
				lc.setSid(new Integer(yellow[i]));
				lc.setYellowNum(new Integer(yellow[i+1]));
				lc.setRedNum(0);
				lc.setSubmitter(wxUserSS.getId());
				lc.setSubTime(new Date());
				lc.setResult(0);
				HibernateDAO.save(lc);
				String content = "添加-编号为：\"" + lc.getId() + "\"的黄牌数";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "黄牌数");
			}

		}
	}
	/**
	 * 
	* @Description: 提交to审核 subMatchToExam的附属方法
	* @author liyvpeng  
	* @date 2018-3-23
	* @version V2.0
	* @return
	* @throws Exception
	 */
	private void subToExamine(String[] t) throws Exception{
			WXUserSS wxUserSS = (WXUserSS) getUser();
			for(int i = 1; i < t.length; i += 2){
				MatchExamine me = new MatchExamine();
				me.setTid(new Integer(t[0]));
				me.setSid(new Integer(t[i]));
				me.setNum(new Integer(t[i+1]));
				me.setLmid(lmid);
				me.setSubmitter(wxUserSS.getId());
				me.setSubTime(new Date());
				me.setResult(0);
				HibernateDAO.save(me);
				String content = "提交-编号为：\"" + me.getId() + "\"的比分情况";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "比分情况");
			}
	}
	/**
	 * 
	* @Description: 分数审核页面跳转/审核/结果
	* @author liyvpeng  
	* @date 2018-3-20
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public String scoreExamine() {
		WXUserSS wxUserSS = (WXUserSS) getUser();
		try {	
			//是否为提交 0是 1不是
			Integer isSubmit = new Integer(getRequest().getParameter("isSubmit"));
			//审核后的提交
			if(isSubmit == 0){
				int submitter = new Integer(getRequest().getParameter("submitter"));
				LeagueMatch lm = (LeagueMatch) HibernateDAO.findById(LeagueMatch.class, lmid);
				List<MatchExamine> oneExamines = leagueDAO.findExamineByLmid(lmid, submitter, lm.getTeamOne());
				List<MatchExamine> twoExamines = leagueDAO.findExamineByLmid(lmid, submitter, lm.getTeamTwo());
				//两个队的id
				int teamOne = oneExamines.get(0).getTid();
				int teamTwo = twoExamines.get(0).getTid();
				//两个队的队名
				Teaminfo tone = (Teaminfo)HibernateDAO.findById(Teaminfo.class, lm.getTeamOne());
				String oneName = tone.getName();
				Teaminfo ttwo = (Teaminfo)HibernateDAO.findById(Teaminfo.class, lm.getTeamTwo());
				String twoName = ttwo.getName();
				//获取联赛id
				int lid = lm.getLeagueId();
				
				//保存红黄牌
				List<LeagueCard> leagueCards = leagueDAO.findLeagueCardsBylmid(lmid, submitter);
				for(int i = 0; i < leagueCards.size(); i++){
					LeagueCard lc = leagueCards.get(i);
					lc.setAuditor(wxUserSS.getId());
					lc.setAutTime(new Date());
					lc.setResult(1);
					HibernateDAO.update(lc);
					String content = "保存-编号为：\"" + lc.getId() + "\"的红黄牌";
					AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "红黄牌");
				}
				//修改射手榜
				int oneNum = updateLeagueSoccer(oneExamines, lid);
				int twoNum = updateLeagueSoccer(twoExamines, lid);
				
				//设置lm比分 将状态改为已编辑
				lm.setOneWin(oneNum);
				lm.setTwoWin(twoNum);
				lm.setFinish(1);
				HibernateDAO.update(lm);
				String content = "修改-编号为：\"" + lm.getId() + "\"的联赛比分情况";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "比分情况");
				
				//根据lid tid 修改积分榜表
				updateLeagueScore(lid, teamOne, oneNum, twoNum, 1);
				updateLeagueScore(lid, teamTwo, oneNum, twoNum, 2);
				
				//填写模板信息
				User user = (User) HibernateDAO.findById(User.class, submitter);
				User my = (User) HibernateDAO.findById(User.class, wxUserSS.getId()); 
				League l = (League) HibernateDAO.findById(League.class, lid); 
				String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_" +
						"token="
						+ TokenThread.accessToken.getToken();
				String info = lm.getTurn() + "/" + lm.getSite();
				String content1 = "裁判:" + my.getNickname() + "通过了" + user.getNickname() + "的申请";
				
				TemplateMessage templateMessage = new TemplateMessage();
				//找到裁判
				List<User> refereesList = leagueDAO.findRefereeGroupByLid(lid);
				//给裁判组发通知
				for(int i = 0; i < refereesList.size(); i++)
					templateMessage.sendMatchExamineMessage(requestUrl, refereesList.get(i).getOpenId(), "比分审核结果", l.getName(), oneName, twoName, info, content1, "");
				//找到t1 t2 所有成员
				List<User> memberList = leagueDAO.findUserByTwoTid(tone.getId(), ttwo.getId());
				//给双方所有成员发通知
				for(int i = 0; i < memberList.size(); i++)
					templateMessage.sendExamineMessage(requestUrl, memberList.get(i).getOpenId(), "比分审核结果", l.getName(), oneName, twoName, info, content1, "");
				HtmlAjax.getJson(getResponse(), true);
			}else{
				//审核or查看
				matchExamine(isExamine);
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 更新射手榜 修改审核状态  (scoreExamine()附属方法)
	* @author liyvpeng  
	* @date 2018-3-24
	* @version V2.0
	* @return 进球数
	* @throws Exception
	 */
	private int updateLeagueSoccer(List<MatchExamine> examines, Integer lid) throws Exception {
		int num = 0;
		WXUserSS wxUserSS = (WXUserSS) getUser();
		for(int i = 0; i < examines.size(); i++){
			MatchExamine examine = examines.get(i);
			//找射手榜
			List<LeagueSoccer> lss = leagueDAO.findSoccerByLidAndSid(lid, examine.getSid());
			//没有则新建
			if(lss.size() == 0){
				LeagueSoccer ls = new LeagueSoccer();
				ls.setLid(lid);
				ls.setNum(examine.getNum());
				ls.setSid(examine.getSid());
				ls.setCreator(wxUserSS.getId());
				ls.setCreateTime(new Date());
				ls.setDelState(0);
				HibernateDAO.save(ls);
				String content = "添加-编号为：\"" + ls.getId() + "\"的射手榜";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "射手榜");
			}else{
				LeagueSoccer ls = lss.get(0);
				ls.setNum(examine.getNum() + ls.getNum());
				ls.setSid(examine.getSid());
				HibernateDAO.update(ls);
				String content = "更新-编号为：\"" + ls.getId() + "\"的射手榜";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "射手榜");
			}
			num += examine.getNum();
			examine.setResult(1);//1同意
			examine.setAuditor(wxUserSS.getId());
			examine.setAutTime(new Date());
			HibernateDAO.update(examine);
			String content = "通过-编号为：\"" + examine.getId() + "\"的比分情况";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "比分情况");
		}
		return num;
	}
	
	/**
	 * 
	* @Description: 更新积分榜 (scoreExamine()附属方法) type字段 1队一 2队二
	* @author liyvpeng  
	* @date 2018-3-24
	* @version V2.0
	* @return 
	* @throws Exception
	 */
	private void updateLeagueScore(int lid, int tid, int oneNum, int twoNum, int type) throws Exception {
		List<LeagueScore> lss = leagueDAO.findScoreByLidAndTid(lid, tid);
		WXUserSS wxUserSS = (WXUserSS) getUser();
		//没有则创建
		if(lss.size() == 0){
			LeagueScore leagueScore = new LeagueScore();
			leagueScore.setLid(lid);
			leagueScore.setTeam(tid);
			leagueScore.setWin(0);
			leagueScore.setLose(0);
			leagueScore.setDraw(0);
			if(type == 1){
				if(oneNum > twoNum) leagueScore.setWin(1);
				if(oneNum < twoNum) leagueScore.setLose(1);
				if(oneNum == twoNum) leagueScore.setDraw(1);
				leagueScore.setInNum(oneNum);
				leagueScore.setOutNum(twoNum);
				leagueScore.setGd(oneNum - twoNum);
			}else{
				if(twoNum > oneNum) leagueScore.setWin(1);
				if(twoNum < oneNum) leagueScore.setLose(1);
				if(twoNum == oneNum) leagueScore.setDraw(1);
				leagueScore.setInNum(twoNum);
				leagueScore.setOutNum(oneNum);
				leagueScore.setGd(twoNum - oneNum);
			}
			leagueScore.setCreator(wxUserSS.getId());
			leagueScore.setCreateTime(new Date());
			leagueScore.setDelState(0);
			HibernateDAO.save(leagueScore);
			String conten = "添加-编号为：\"" + leagueScore.getId() + "\"的积分榜";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", conten, "积分榜");
		}else{
			LeagueScore leagueScore = lss.get(0);
			if(type == 1){
				if(oneNum > twoNum) leagueScore.setWin(leagueScore.getWin() + 1);
				if(oneNum < twoNum) leagueScore.setLose(leagueScore.getLose() + 1);
				if(oneNum == twoNum) leagueScore.setDraw(leagueScore.getLose() + 1);
				leagueScore.setInNum(oneNum + leagueScore.getInNum());
				leagueScore.setOutNum(twoNum + leagueScore.getOutNum());
				leagueScore.setGd(oneNum - twoNum + leagueScore.getGd());
			}else {
				if(twoNum > oneNum) leagueScore.setWin(leagueScore.getWin() + 1);
				if(twoNum < oneNum) leagueScore.setLose(leagueScore.getLose() + 1);
				if(twoNum == oneNum) leagueScore.setDraw(leagueScore.getLose() + 1);
				leagueScore.setInNum(twoNum + leagueScore.getInNum());
				leagueScore.setOutNum(oneNum + leagueScore.getOutNum());
				leagueScore.setGd(twoNum - oneNum + leagueScore.getGd());
			}		
			leagueScore.setModifier(wxUserSS.getId());
			leagueScore.setModifyTime(new Date());
			HibernateDAO.update(leagueScore);
			String conten = "修改-编号为：\"" + leagueScore.getId() + "\"的积分榜";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", conten, "积分榜");
		}
	}
	/**
	 * 
	* @Description: 比分审核跳转or查看(scoreExamine()的附属)
	* @author liyvpeng  
	* @date 2018-3-24
	* @version V2.0
	* @return
	* @throws Exception
	 */
	private void matchExamine(Integer isExamine) throws Exception{
		WXUserSS wxUserSS = (WXUserSS) getUser();
		LeagueMatch lm = (LeagueMatch) HibernateDAO.findById(LeagueMatch.class, lmid);
		//0自己审核中的  1审核人看  2审核通过的
		Integer showType = new Integer(getRequest().getParameter("showType"));
		//获取队名
		Teaminfo tone = (Teaminfo)HibernateDAO.findById(Teaminfo.class, lm.getTeamOne());
		t1 = tone.getName();
		Teaminfo ttwo = (Teaminfo)HibernateDAO.findById(Teaminfo.class, lm.getTeamTwo());
		t2 = ttwo.getName();
		//自己看
		if(showType == 0){
			uid = wxUserSS.getId();
		}
		//各自进球情况
		oneExamine = leagueDAO.findscoreExamineByTid(lmid, uid, lm.getTeamOne(), showType);
		twoExamine = leagueDAO.findscoreExamineByTid(lmid, uid, lm.getTeamTwo(), showType);
		//红黄牌
		redList = leagueDAO.findRedCardBylmid(lmid, uid, showType);
		yellowList = leagueDAO.findYellowCardBylmid(lmid, uid, showType);
		
		
	}
	
	/**
	 * 
	* @Description: 拒绝比分申请
	* @author liyvpeng  
	* @date 2018-3-22
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public void refuseExamine() throws Exception {
		WXUserSS wxUserSS = (WXUserSS) getUser();
		Integer submitter = new Integer(getRequest().getParameter("submitter"));
		try {
			//拒绝红黄牌
			List<LeagueCard> leagueCards = leagueDAO.findLeagueCardsBylmid(lmid, submitter);
			for(int i = 0; i < leagueCards.size(); i++){
				LeagueCard lc = leagueCards.get(i);
				lc.setAuditor(wxUserSS.getId());
				lc.setAutTime(new Date());
				lc.setResult(2);
				HibernateDAO.update(lc);
				String content = "拒绝-编号为：\"" + lc.getId() + "\"的红黄牌";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "红黄牌");
			}
			//拒绝申请
			List<MatchExamine> mExamines = leagueDAO.findExamineByLmidAndUid(lmid, submitter);
			for(int i = 0; i < mExamines.size(); i++){
				MatchExamine me = mExamines.get(i);
				me.setResult(2);//2拒绝
				me.setAuditor(wxUserSS.getId());
				me.setAutTime(new Date());
				HibernateDAO.update(mExamines.get(i));
			}
			
			LeagueMatch lm = (LeagueMatch) HibernateDAO.findById(LeagueMatch.class, lmid);
			League l = (League) HibernateDAO.findById(League.class, lm.getLeagueId() );
			
			Teaminfo tone = (Teaminfo)HibernateDAO.findById(Teaminfo.class, lm.getTeamOne());
			t1 = tone.getName();
			Teaminfo ttwo = (Teaminfo)HibernateDAO.findById(Teaminfo.class, lm.getTeamTwo());
			t2 = ttwo.getName();
			
			User my = (User) HibernateDAO.findById(User.class, wxUserSS.getId());
			User user = (User) HibernateDAO.findById(User.class, submitter);
			User u1 = (User) HibernateDAO.findById(User.class, tone.getCaptainId());
			User u2 = (User) HibernateDAO.findById(User.class, ttwo.getCaptainId());
			//填写模板消息并发给提交人
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_" +
					"token="
					+ TokenThread.accessToken.getToken();
			String reason = getRequest().getParameter("reason");
			String info = lm.getTurn() + "/" + lm.getSite();
			String content = "裁判:" + my.getNickname() + "拒绝了"+user.getNickname()+"的申请，理由是:" + reason;
			TemplateMessage templateMessage = new TemplateMessage();
			templateMessage.sendExamineMessage(requestUrl, u1.getOpenId(), "比分审核结果", l.getName(), t1, t2, info, content, "");
			templateMessage.sendExamineMessage(requestUrl, u2.getOpenId(), "比分审核结果", l.getName(), t1, t2, info, content, "");
			HtmlAjax.getJson(getResponse(), true);
		}catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	
	public Integer getId() {
		return id;
	}

	public List<Object[]> getLeagueRefereeList() {
		return leagueRefereeList;
	}

	public void setLeagueRefereeList(List<Object[]> leagueRefereeList) {
		this.leagueRefereeList = leagueRefereeList;
	}

	@SuppressWarnings("static-access")
	public void setId(Integer id) {
		this.id = id;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public List<Object[]> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<Object[]> scoreList) {
		this.scoreList = scoreList;
	}

	public List<Object[]> getShootList() {
		return shootList;
	}

	public void setShootList(List<Object[]> shootList) {
		this.shootList = shootList;
	}

	public List<Object[]> getMyReferee() {
		return leagueScore;
	}

	public void setMyReferee(List<Object[]> myReferee) {
		this.leagueScore = myReferee;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public List<Object[]> getLeagueScore() {
		return leagueScore;
	}

	public void setLeagueScore(List<Object[]> leagueScore) {
		this.leagueScore = leagueScore;
	}

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}

	public LeagueDAO getLeagueDAO() {
		return leagueDAO;
	}

	public void setLeagueDAO(LeagueDAO leagueDAO) {
		this.leagueDAO = leagueDAO;
	}

	public List<League> getMyLeagues() {
		return myLeagues;
	}

	public void setMyLeagues(List<League> myLeagues) {
		this.myLeagues = myLeagues;
	}

	public List<Teaminfo> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Teaminfo> teamList) {
		this.teamList = teamList;
	}

	public List<League> getJoinLeagues() {
		return joinLeagues;
	}

	public List<LeagueMatch> getTurns() {
		return turns;
	}

	public void setTurns(List<LeagueMatch> turns) {
		this.turns = turns;
	}

	public void setTurn(Integer turn) {
		this.turn = turn;
	}

	public List<Object[]> getMatchs() {
		return matchs;
	}

	public void setMatchs(List<Object[]> matchs) {
		this.matchs = matchs;
	}

	public void setJoinLeagues(List<League> joinLeagues) {
		this.joinLeagues = joinLeagues;
	}

	public List<LeagueMatch> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<LeagueMatch> scheduleList) {
		this.scheduleList = scheduleList;
	}

	public Integer getTurn() {
		return turn;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Object[]> getReferee() {
		return referee;
	}

	public void setReferee(List<Object[]> referee) {
		this.referee = referee;
	}

	public LeagueMatch getMatch() {
		return match;
	}

	public void setMatch(LeagueMatch match) {
		this.match = match;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}



	public List<Object[]> getOneExamine() {
		return oneExamine;
	}

	public void setOneExamine(List<Object[]> oneExamine) {
		this.oneExamine = oneExamine;
	}

	public List<Object[]> getTwoExamine() {
		return twoExamine;
	}

	public void setTwoExamine(List<Object[]> twoExamine) {
		this.twoExamine = twoExamine;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getIsExamine() {
		return isExamine;
	}

	public void setIsExamine(Integer isExamine) {
		this.isExamine = isExamine;
	}

	public List<Object[]> getRedList() {
		return redList;
	}

	public void setRedList(List<Object[]> redList) {
		this.redList = redList;
	}

	public ApplyLeague getReferees() {
		return referees;
	}

	public void setReferees(ApplyLeague referees) {
		this.referees = referees;
	}

	public List<Object[]> getYellowList() {
		return yellowList;
	}

	public void setYellowList(List<Object[]> yellowList) {
		this.yellowList = yellowList;
	}
	
	
}
