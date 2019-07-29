package com.ningxun.sign.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.leave.vo.Leave;
import com.ningxun.race.vo.Race;
import com.ningxun.score.dao.ScoreDao;
import com.ningxun.score.vo.Racescore;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.sign.dao.signDao;
import com.ningxun.sign.vo.Sign;
import com.ningxun.tools.TemplateMessage;
import com.ningxun.train.vo.Train;
import com.ningxun.util.HtmlAjax;
import com.ningxun.wxuserinfo.dao.WXUserDAO;
import com.ningxun.wxuserinfo.vo.WXUser;
import com.opensymphony.xwork2.ActionContext;

/**
*<li>技术支持:河北凝讯科技有限公司<br/>
*<li>项目名称: rongqiu<br/>
*<li>文件名: signAction.java<br/>
*<li>创建时间: 2017年7月23日 下午9:32:48<br/>
*
*@author 梦强
*@version [v1.00]
*/
public class signAction extends BaseSupportAction {
	private List raceList;
	private List trainList;
	private List list;
	private Sign sign = new Sign();
	private Race race = new Race();
	private Racescore racescore = new Racescore();
	private Leave leave = new Leave();
	private signDao signDao = new signDao();
	private ScoreDao scoreDao = new ScoreDao();
	private WXUserDAO wxUserDAO = new WXUserDAO();
	private HtmlAjax htmlAjax = new HtmlAjax();
	private Log log = LogFactory.getLog(getClass());
	//签到表主键id
	private Integer sid;
	//比赛id
	private Integer rid;
	//训练id
	private Integer trid;
	//签到状态值
	private Integer type;
	//用户id
	private Integer uid;
	
	
	/**
	 * 
	* 描述: 展示比赛和训练的签到列表(废弃)，由于签到页面比赛训练合并到一起，本方法作废<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月24日/上午8:19:00<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String findRTList(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			raceList = signDao.findRaceList(loginUser.getId());
			trainList = signDao.findTrainList(loginUser.getId());
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 展示所有比赛训练<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-8-13/下午4:45:13<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String findAllList(){
		try {
			//获取当前登录用户
			WXUserSS loginUser = (WXUserSS) getUser();
			//根据用户id查询出签到信息的列表
			list = signDao.findAllListTRList(loginUser.getId(),pageNo,PAGE_SIZE);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: ajax下拉加载更多比赛训练<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-13/下午4:46:45<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void ajaxLoadMoreTRList() throws Exception {
		try {
			//获取当前登录用户
			WXUserSS loginUser = (WXUserSS) getUser();
			//根据用户id拼接sql语句
			String sql = signDao.getAListllTRListSql(loginUser.getId());
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, signDao.getTagName(), pageNo, 0);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	/**
	 * 
	* 描述: ajax点击确认比赛签到<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月24日/上午8:25:51<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String ajaxSign(){
		try {
			//获取当前登录用户
			WXUserSS loginUser = (WXUserSS) getUser();
			//根据前台传回的sid信息查询出该条签到记录
			sign = (Sign) HibernateDAO.findById(Sign.class, sid);
			//根据查询出的签到信息获取比赛信息，本处不存在查询不出比赛信息的情况，故不做判断
			Race race = (Race) HibernateDAO.findById(Race.class, sign.getRaceortrainId());
			//获取当前时间
			Date startTime = new Date();
			//判断提前签到时间，0表示提前半小时，1表示提前一小时，2表示提前2小时
			if (race.getSignTime() == 0) {
				startTime = new Date(race.getStartTime().getTime()-30*60*1000);
			}else if (race.getSignTime() == 1) {
				startTime = new Date(race.getStartTime().getTime()-60*60*1000);
			}else if (race.getSignTime() == 2) {
				startTime = new Date(race.getStartTime().getTime()-120*60*1000);
			}
			//判断是否在可签到时间内签单
			if (race.getEndTime().getTime() > new Date().getTime() && startTime.getTime() < new Date().getTime()) {
				//判断签到标识状态，null（正常情况不会出现），0表示可以签到
				if (sign.getIsAttendance() == null) {
					sign.setAttendanceType(1);//设置签到类型为点击签到
					sign.setIsAttendance(1);//设置签到装填为已签到
					sign.setModifyTime(new Date());//获取当前时间为修改时间
					sign.setModifyUser(loginUser.getId());//获取当前登录用户为修改人
					sign.setSignUp(1);//成功签到后设置报名信息为已报名
					Integer updateId = HibernateDAO.update(sign);//更新签到信息
					if (updateId != null) {
						// 操作日志
						String content = "编辑-编号为：\"" + updateId + "\"的签到信息";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "编辑");
					}
					//向前台返回签到成功信息
					HtmlAjax.getJson(getResponse(), 1);
					
					Racescore raceScore = scoreDao.findScore(sign.getUid(), sign.getRaceortrainId(), sign.getTid());
					if (raceScore == null) {
						
						//添加得分记录
						racescore.setTid(sign.getTid());//球队id
						racescore.setUid(sign.getUid());//用户id
						racescore.setRaceId(sign.getRaceortrainId());//比赛id
						
						racescore.setDelState(0);//删除标志
						racescore.setCreateTime(new Date());//创建时间
						racescore.setCreateUser(loginUser.getId());//创建人
						
						racescore.setGetScore(0);//添加得分置0
						racescore.setYellowCard(0);//添加黄牌置0
						racescore.setRedCard(0);//添加红牌置0
						
						HibernateDAO.save(racescore);
						
					}
				//1或2表示无法签到
				}else if (sign.getIsAttendance() == 1 || sign.getIsAttendance() == 2) {
					//向前台返回签到失败信息
					HtmlAjax.getJson(getResponse(), 4);
				}else {
					sign.setAttendanceType(1);//设置签到状态为点击签到
					sign.setIsAttendance(1);//设置签到状态为已签到
					sign.setModifyTime(new Date());//获取当前时间为修改时间
					sign.setModifyUser(loginUser.getId());//获取当前用户为修改人
					sign.setSignUp(1);//成功签到后设置报名信息为已报名
					Integer updateId = HibernateDAO.update(sign);//更新签到信息
					
					if (updateId != null) {
						// 操作日志
						String content = "编辑-编号为：\"" + updateId + "\"的签到信息";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "编辑");
					}
					//向前台返回签到成功信息
					HtmlAjax.getJson(getResponse(), 1);
					
					Racescore raceScore = scoreDao.findScore(sign.getUid(), sign.getRaceortrainId(), sign.getTid());
					if (raceScore == null) {
						
						//添加得分记录
						racescore.setTid(sign.getTid());//球队id
						racescore.setUid(sign.getUid());//用户id
						racescore.setRaceId(sign.getRaceortrainId());//比赛id
						
						racescore.setDelState(0);//删除标志
						racescore.setCreateTime(new Date());//创建时间
						racescore.setCreateUser(loginUser.getId());//创建人
						
						racescore.setGetScore(0);//添加得分置0
						racescore.setYellowCard(0);//添加黄牌置0
						racescore.setRedCard(0);//添加红牌置0
						
						HibernateDAO.save(racescore);
						
					}
				}
			//表示已过签到时间，无法签到
			}else if(race.getEndTime().getTime() < new Date().getTime()){
				//向前台返回签到超时提示
				HtmlAjax.getJson(getResponse(), 2);
			}else {
				//向前台返回签到未开始提示
				HtmlAjax.getJson(getResponse(), 3);
			}
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: ajax训练签到<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017年7月24日/下午3:16:18<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String ajaxTSign(){
		try {
			//获取当前登录用户
			WXUserSS loginUser = (WXUserSS) getUser();
			//根据前台传回的sid信息查询出该条签到记录
			sign = (Sign) HibernateDAO.findById(Sign.class, sid);
			//根据查询出的签到信息获取训练信息，本处不存在查询不出训练信息的情况，故不做判断
			Train train = (Train) HibernateDAO.findById(Train.class, sign.getRaceortrainId());
			//获取当前时间
			Date startTime = new Date();
			//判断提前签到时间，0表示提前半小时，1表示提前一小时，2表示提前2小时
			if (train.getSignTime() == 0) {
				startTime = new Date(train.getStartTime().getTime() - 30*60*1000);
			}else if (train.getSignTime() == 1) {
				startTime = new Date(train.getStartTime().getTime() - 60*60*1000);
			}else if (train.getSignTime() == 2) {
				startTime = new Date(train.getStartTime().getTime() - 120*60*1000);
			}
			//判断是否在可签到时间内签单
			if (train.getEndTime().getTime() > new Date().getTime() && startTime.getTime() <new Date().getTime()) {
				//1，2表示无法签到
				if (sign.getIsAttendance() == 1 || sign.getIsAttendance() == 2) {
					//向前台返回签到失败信息
					HtmlAjax.getJson(getResponse(), 4);
				}else {
					sign.setAttendanceType(1);//设置签到状态为点击签到
					sign.setIsAttendance(1);//设置签到状态为已签到
					sign.setModifyTime(new Date());//获取当前时间为修改时间
					sign.setModifyUser(loginUser.getId());//获取当前登录用户为修改人
					Integer updateId = HibernateDAO.update(sign);//更新签到信息
					HtmlAjax.getJson(getResponse(), 1);//向前台返回签到成功信息
					if (updateId != null) {
						// 操作日志
						String content = "编辑-编号为：\"" + updateId + "\"的签到信息";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "编辑");
					}
				}
			//表示签到已结束
			}else if (train.getEndTime().getTime() < new Date().getTime()) {
				//向前台返回签到超时提示
				HtmlAjax.getJson(getResponse(), 2);
			}else {
				//向前台返回签到未开始提示
				HtmlAjax.getJson(getResponse(), 3);
			}
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 
	* 描述: 查询比赛签到名单<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月24日/上午10:09:58<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String findRSignList(){
		try {
			race = (Race) HibernateDAO.findById(Race.class, rid);
			//获取当前时间
			Date startTime = new Date();
			//判断提前签到时间，0表示提前半小时，1表示提前一小时，2表示提前2小时
			if (race.getSignTime() == 0) {
				startTime = new Date(race.getStartTime().getTime()-30*60*1000);
			}else if (race.getSignTime() == 1) {
				startTime = new Date(race.getStartTime().getTime()-60*60*1000);
			}else if (race.getSignTime() == 2) {
				startTime = new Date(race.getStartTime().getTime()-120*60*1000);
			}
			ActionContext.getContext().put("startTime", startTime);
			if (type == 4) {
				//此处为队员看到的签到信息列表，跳转到单纯的展示页面
				raceList = signDao.findRSignUser(rid);
				
				return "teamMember";
			}else {
				//此处为管理员看到的嵌套信息列表，跳转可修改队员签到信息页面
				raceList = signDao.findRSignUser(rid);
				return SUCCESS;
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 查询训练签到名单<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月24日/上午10:11:34<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String findTSignList(){
		try {
			Train train = (Train) HibernateDAO.findById(Train.class, trid);
			//获取当前时间
			Date startTime = new Date();
			//判断提前签到时间，0表示提前半小时，1表示提前一小时，2表示提前2小时
			if (train.getSignTime() == 0) {
				startTime = new Date(train.getStartTime().getTime()-30*60*1000);
			}else if (train.getSignTime() == 1) {
				startTime = new Date(train.getStartTime().getTime()-60*60*1000);
			}else if (train.getSignTime() == 2) {
				startTime = new Date(train.getStartTime().getTime()-120*60*1000);
			}
			ActionContext.getContext().put("startTime", startTime);
			if (type == 4) {
				//此处为队员看到的签到信息列表，跳转到单纯的展示页面，训练借用比赛的list集合，减少页面的判断
				raceList = signDao.findTSignUser(trid);
				return "teamMember";
			}else {
				//此处为管理员看到的嵌套信息列表，跳转可修改队员签到信息页面，训练借用比赛的list集合，减少页面的判断
				raceList = signDao.findTSignUser(trid);
				return SUCCESS;
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: ajax修改签到名单状态值，此方法只有队长会用到<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月24日/下午4:58:56<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String ajaxChangeType(){
		try {
			//根据前台传回sid查询签到信息
			sign = (Sign) HibernateDAO.findById(Sign.class, sid);
			if (sign != null) {
				//获取当前登录人信息
				WXUserSS loginUser = (WXUserSS) getUser();
				//判断是否到签到时间
				if (sign.getCreateTime().getTime() > new Date().getTime()) {
					HtmlAjax.getJson(getResponse(), 3);
					return NONE;
				}
				//队长对自己的签到信息只能补签，无法改为未到
				if (loginUser.getId() == sign.getUid()) {
					if (sign.getIsAttendance() == 0 || sign.getIsAttendance() == null) {
						sign.setIsAttendance(2);//修改签到状态为已签到
					}
					sign.setModifyTime(new Date());//获取当前时间为修改时间
					sign.setModifyUser(loginUser.getId());//获取当前登录人为修改人
					Integer updateId = HibernateDAO.update(sign);//更新签到信息
					if (updateId != null) {
						
						if (sign.getRaceortrain()==2) {
							
							Racescore raceScore = scoreDao.findScore(sign.getUid(), sign.getRaceortrainId(), sign.getTid());
							if (raceScore == null) {
								
								//添加得分记录
								racescore.setTid(sign.getTid());//球队id
								racescore.setUid(sign.getUid());//用户id
								racescore.setRaceId(sign.getRaceortrainId());//比赛id
								
								racescore.setDelState(0);//删除标志
								racescore.setCreateTime(new Date());//创建时间
								racescore.setCreateUser(loginUser.getId());//创建人
								
								racescore.setGetScore(0);//添加得分置0
								racescore.setYellowCard(0);//添加黄牌置0
								racescore.setRedCard(0);//添加红牌置0
								
								HibernateDAO.save(racescore);
								
							}
							
						}
						
						// 操作日志
						String content = "编辑-编号为：\"" + updateId + "\"的签到信息";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "编辑");
					}
					//向前台返回成功信息
					HtmlAjax.getJson(getResponse(), 1);
				}else {
					//队长给队员修改签到信息
					if (sign.getIsAttendance() == 1 || sign.getIsAttendance() == 2) {
						//修改队员签到状态为未到
						sign.setIsAttendance(3);
					}else if (sign.getIsAttendance() == 0) {
						//修改队员签到状态为队长补签
						sign.setIsAttendance(2);
					}else {
						//修改队员签到状态为队长补签
						sign.setIsAttendance(2);
					}
					sign.setModifyTime(new Date());
					sign.setModifyUser(loginUser.getId());
					Integer updateId = HibernateDAO.update(sign);
					if (updateId != null) {
						
						if (sign.getRaceortrain()==2) {
							
							Racescore raceScore = scoreDao.findScore(sign.getUid(), sign.getRaceortrainId(), sign.getTid());
							if (raceScore == null) {
								//添加得分记录
								racescore.setTid(sign.getTid());//球队id
								racescore.setUid(sign.getUid());//用户id
								racescore.setRaceId(sign.getRaceortrainId());//比赛id
								
								racescore.setDelState(0);//删除标志
								racescore.setCreateTime(new Date());//创建时间
								racescore.setCreateUser(loginUser.getId());//创建人
								
								racescore.setGetScore(0);//添加得分置0
								racescore.setYellowCard(0);//添加黄牌置0
								racescore.setRedCard(0);//添加红牌置0
								
								HibernateDAO.save(racescore);
								
							}
							
						}
						
						// 操作日志
						String content = "编辑-编号为：\"" + updateId + "\"的签到信息";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "编辑");
					}
					//向前台返回操作成功信息
					HtmlAjax.getJson(getResponse(), 2);
				}
			}else {
				//向前台返回签到失败信息
				HtmlAjax.getJson(getResponse(), false);
			}
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 
	* 描述: 摇一摇签到（使用微信摇一摇设备），利用微信的摇一摇功能完成签到<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017年8月3日/上午10:58:56<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String shakeSign(String xml){
		try {
			//获取微信向服务器发送的xml信息
			Map<String, String> xml2map = MessageUtil.xml2map(xml);
			//从xml中获取uuid信息，uuid即为设备的id，为设备的标识信息
			String uuid = xml2map.get("Uuid");
			//从xml中获取用户的openid，用于查询用户信息
			String FromUserName = xml2map.get("FromUserName");
			Date now = new Date();//获取当前时间
			Date startTime = new Date();
			Date endTime = new Date();
			Integer signTime = -1;
			int state = 0;//1签到成功，2签到失败
			//根据openid查询用户信息
			WXUser wxUser = wxUserDAO.findWxUserByOpenId(FromUserName);
			if (wxUser != null) {
				//根据查询出的用户信息查询出可签到的比赛
				List<Object[]> signList = signDao.findSignList(uuid, wxUser.getId());
				for (int i = 0; i < signList.size(); i++) {
					//获取提前签到时间
					signTime = (Integer) signList.get(i)[5];
					//获取开始时间
					startTime = (Date) signList.get(i)[3];
					//获取结束时间
					endTime = (Date) signList.get(i)[4];
					//判断提前签到时间，0表示提前半小时，1表示提前一小时，2表示提前2小时
					if (signTime == 0) {
						startTime = new Date(startTime.getTime() - 30*60*1000);
					}else if (signTime == 1) {
						startTime = new Date(startTime.getTime() - 60*60*1000);
					}else if (signTime == 2) {
						startTime = new Date(startTime.getTime() - 120*60*1000);
					}
					if (endTime.getTime() > now.getTime() && startTime.getTime() <now.getTime()) {
						sign = (Sign) HibernateDAO.findById(Sign.class, (Integer) signList.get(i)[0]);
						if(sign.getIsAttendance() != 3){
							sign.setAttendanceType(2);//设置签到状态为摇一摇签到
							sign.setIsAttendance(1);//设置签到状态为已签到
							sign.setModifyTime(now);//设置修改时间为当前时间
							sign.setModifyUser((Integer) signList.get(i)[2]);//设置修改人为摇一摇发起人
							Integer updateId = HibernateDAO.update(sign);//更新签到信息
							state = 1;
						}
					}else if (endTime.getTime() < now.getTime()) {
						state = 1;
					}else {
						state = 1;
					}
				}
				if (state == 1) {
					//签到成功，发送签到成功模板消息
					TemplateMessage.shackMes("签到成功", wxUser.getNickname(), wxUser.getOpenId());
					
					if (sign.getRaceortrain()==2) {
					
						Racescore raceScore = scoreDao.findScore(sign.getUid(), sign.getRaceortrainId(), sign.getTid());
						if (raceScore == null) {
							
							//添加得分记录
							racescore.setTid(sign.getTid());//球队id
							racescore.setUid(sign.getUid());//用户id
							racescore.setRaceId(sign.getRaceortrainId());//比赛id
							
							racescore.setDelState(0);//删除标志
							racescore.setCreateTime(new Date());//创建时间
							racescore.setCreateUser(wxUser.getId());//创建人
							
							racescore.setGetScore(0);//添加得分置0
							racescore.setYellowCard(0);//添加黄牌置0
							racescore.setRedCard(0);//添加红牌置0
							
							HibernateDAO.save(racescore);
							
						}
						
					}
				}else {
					//签到失败，发送签到失败模板消息
					TemplateMessage.shackMes("签到失败", wxUser.getNickname(), wxUser.getOpenId());
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
		
	}
	
	/**
	 * 
	* 描述: 队长查看可以代假的名单<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017年8月3日/上午10:58:56<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String showLeaveList(){
		try {
			if (type == 1) {
				//队长查看训练已请假未请假名单
				list = signDao.findTLeavelist(rid);
			}else {
				//队长查看比赛已请假未请假名单
				list = signDao.findRLeaveList(rid);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: ajax队长代替队员请假<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017年8月3日/上午10:58:56<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String ajaxLeave(){
		try {
			sign = (Sign) HibernateDAO.findById(Sign.class, sid);
			leave = signDao.findUserIsLeave(sign.getUid(), sign.getRaceortrain(), sign.getRaceortrainId());
			//根据type判断是比赛还是训练签到
			if (sign.getRaceortrain() == 1) {
				leave.setRaceOrTrain(1);
			}else {
				leave.setRaceOrTrain(2);
			}
			leave.setRaceOrTrainId(sign.getRaceortrainId());
			leave.setReason("队长代替队员请假");
			leave.setCreateTime(new Date());
			leave.setCreateUser(sign.getUid());
			leave.setDelState(0);
			HibernateDAO.merge(leave);
			
			if (sign != null && sign.getSignUp() != null) {
				if (sign.getSignUp().equals(1)) {//是否已报名
					sign.setSignUp(0);//修改删除标志
					HibernateDAO.merge(sign);
				}
			}
			//向前台返回签到成功信息
			HtmlAjax.getJson(getResponse(), true);
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 这里用一句话描述这个方法的作用<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-10-6/下午11:46:30<br/>
	* @author 纪梦强  
	* @version V1.0
	 */
	public String ajaxSignUp(){
		try {
			sign = (Sign) HibernateDAO.findById(Sign.class, sid);
			if (sign.getSignUp() == 0) {
				sign.setSignUp(1);
			}else {
				//向前台返回修改失败信息
				HtmlAjax.getJson(getResponse(), 2);
				return NONE;
			}
			if (sign.getIsAttendance() == 1 || sign.getIsAttendance() == 2) {
				//向前台返回该队员已经签到的信息
				if (sign.getSignUp() == 1) {
					HtmlAjax.getJson(getResponse(), 2);
				}else {
					HtmlAjax.getJson(getResponse(), 3);
				}
				return NONE;
			}
			HibernateDAO.merge(sign);
			leave = signDao.findUserIsLeave(sign.getUid(), sign.getRaceortrain(), sign.getRaceortrainId());
			HibernateDAO.delete(leave);
			HtmlAjax.getJson(getResponse(), 1);
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}
	
	public Sign getSign() {
		return sign;
	}
	public void setSign(Sign sign) {
		this.sign = sign;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public List getRaceList() {
		return raceList;
	}
	public void setRaceList(List raceList) {
		this.raceList = raceList;
	}
	public List getTrainList() {
		return trainList;
	}
	public void setTrainList(List trainList) {
		this.trainList = trainList;
	}
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Integer getTrid() {
		return trid;
	}
	public void setTrid(Integer trid) {
		this.trid = trid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Leave getLeave() {
		return leave;
	}
	public void setLeave(Leave leave) {
		this.leave = leave;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Race getRace() {
		return race;
	}
	public void setRace(Race race) {
		this.race = race;
	}
}
