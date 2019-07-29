package com.ningxun.apply.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.apply.dao.ApplyDao;
import com.ningxun.apply.vo.Apply;
import com.ningxun.apply.vo.ApplyLeague;
import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.league.dao.LeagueDAO;
import com.ningxun.league.vo.League;
import com.ningxun.league.vo.User;
import com.ningxun.position.dao.PositionDao;
import com.ningxun.position.vo.Position;
import com.ningxun.race.dao.RaceDAO;
import com.ningxun.race.vo.Race;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.sign.vo.Sign;
import com.ningxun.team.dao.TeaminfoDao;
import com.ningxun.team.vo.UserTeam;
import com.ningxun.tools.TemplateMessage;
import com.ningxun.train.dao.TrainDAO;
import com.ningxun.train.vo.Train;
import com.ningxun.util.HtmlAjax;
import com.ningxun.wxuserinfo.vo.WXUser;
import com.opensymphony.xwork2.ActionContext;

/**
* 申请Action
* @author hujian
* @date 2017年7月20日
* @version 1.0
*/
public class ApplyAction extends BaseSupportAction {
	
	private static final long serialVersionUID = 1L;
	private ApplyDao applyDao = new ApplyDao();
	private TeaminfoDao teaminfoDao = new TeaminfoDao();
	private RaceDAO raceDAO = new RaceDAO();
	private TrainDAO trainDAO = new TrainDAO();
	private PositionDao positionDao = new PositionDao();
	private LeagueDAO leagueDAO = new LeagueDAO();
	private HtmlAjax htmlAjax = new HtmlAjax();
	private TemplateMessage templateMessage = new TemplateMessage();
	private Log log = LogFactory.getLog(getClass());
	private Integer tid;//球队id
	private Integer uid;//用户id
	private String reason;//拒绝理由
	private Integer lid;//联赛id
	

	/**
	 * 查询申请用户列表
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	public String applyList() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(tid, loginUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				//跳转提示被移除页面
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				addActionMessage("您无权进行此操作！");
				return INPUT;
			}
			//待处理列表
			List<WXUser> waitHandleList = applyDao.getApplyWaitHandleList(tid);
			//已处理列表
			List<Object[]> handleList = applyDao.getApplyHandleList(tid,pageNo,PAGE_SIZE);
			//为了统计总数的处理列表
			List<Object[]> tempHandleList = applyDao.getApplyHandleList(tid);
			
			ActionContext.getContext().put("waitHandleList", waitHandleList);
			ActionContext.getContext().put("handleList", handleList);
			ActionContext.getContext().put("tempHandleList", tempHandleList);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 查看用户申请联赛列表
	 * @author liudongxin
	 * @date 2018年3月13日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String applyLeagueList() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//待处理列表
			List<WXUser> waitLeagueHandleList = applyDao.getApplyLeagueWaitHandleList(lid);
			//已处理列表
			List<Object[]> leagueHandleList = applyDao.getApplyLeagueHandleList(lid,pageNo,PAGE_SIZE);
			//为了统计总数的处理列表
			List<Object[]> tempLeagueHandleList = applyDao.getApplyLeagueHandleList(lid);
			
			ActionContext.getContext().put("waitLeagueHandleList", waitLeagueHandleList);
			ActionContext.getContext().put("leagueHandleList", leagueHandleList);
			ActionContext.getContext().put("tempLeagueHandleList", tempLeagueHandleList);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	
	/**
	 * 上拉加载更多已经处理的列表
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	public void ajaxLoadMoreApplyHandleList() throws Exception {
		try {
			String sql = applyDao.getApplyHandleListSql(tid);
			String sql2 = applyDao.getApplyLeagueHandleListSql(lid); //联赛用
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, applyDao.getTagName(), pageNo, 0);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 我的申请记录
	 * @author liudongxin
	 * @date 2018年3月17日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String myApply(){
		try {
			//我的申请记录
			WXUserSS loginUser = (WXUserSS) getUser();
			List<Object[]> myApplyList = applyDao.getMyApplyList(loginUser.getId());
			ActionContext.getContext().getValueStack().set("myApplyList", myApplyList);
			List<Object[]> myApplyLeagueList = applyDao.getMyApplyLeagueList(loginUser.getId());
			ActionContext.getContext().getValueStack().set("myApplyLeagueList", myApplyLeagueList);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 申请球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	
	public void applyTeam() throws Exception{
		try {
			//先查询是否已经申请,而且是处于待处理或者同意阶段
			//当前登录用户
			WXUserSS loginUser = (WXUserSS) getUser();
			//校验该用户是否是申请球队的队长,因为只有队长在apply表中没有数据
			boolean isLeader= applyDao.checkIsLeader(tid, loginUser.getId());
			boolean check = applyDao.checkApply(tid,loginUser.getId());
			if (isLeader) {
				//是该球队的队长
				HtmlAjax.getJson(getResponse(), 2);//2代表是队长
				return;
			}
			if (check) {
				//处于申请待处理状态或者已经同意
				HtmlAjax.getJson(getResponse(), 3);//3代表代处理或者已同意
				return;
			}
			Apply apply = new Apply();
			apply.setId(null);
			apply.setTid(tid);
			apply.setUid(loginUser.getId());
			apply.setApplyHandle(Apply.APPLY_HANDLE_WAITHANDLE);
			apply.setApplyTime(new Date());
			if (HibernateDAO.save(apply) == null ) {
				HtmlAjax.getJson(getResponse(), 1);//1代表处理失败
				return;
			}
			HtmlAjax.getJson(getResponse(), 0);//0代表成功处理
			//查询该球队的所有管理员
			List<WXUser> adminList = teaminfoDao.getAdminListByTid(tid);
			WXUser applyUser = (WXUser) HibernateDAO.findById(WXUser.class, loginUser.getId());
			//请求路径
			String requestURL = getRequest().getRequestURL().toString();
			//拼接完整url
			String url = requestURL.replace("applyTeam", "applyList")+"?tid="+tid;
			templateMessage.sendApplyMes(applyUser, adminList,url);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), 1);//1代表处理失败
		}
	}
	
	/**
	 * 申请裁判
	 * @author 
	 * @date 2018年3月13日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	public void applyReferee() throws Exception{

		try {
			//先查询是否已经申请,而且是处于待处理或者同意阶段
			//当前登录用户
			WXUserSS loginUser = (WXUserSS) getUser();
			int lid= new Integer(getRequest().getParameter("lid"));
			//boolean isCreator= inviteDao.checkIsCreator(lid, loginUser.getId());
			boolean check = applyDao.checkApplyLeague(lid,loginUser.getId());
//			/*if (isCreator) {
//				//是该联赛的创建人
//				HtmlAjax.getJson(getResponse(), 2);//2代表是创建人
//				return;
//			}*/
		   if (check) {
				//处于邀请待处理状态或者已经同意
			HtmlAjax.getJson(getResponse(), 3);//3代表代处理或者已同意
			return;
			}
			ApplyLeague applyLeague = new ApplyLeague();
			applyLeague.setId(null);
			applyLeague.setUid(loginUser.getId());
			applyLeague.setLid(lid);
			applyLeague.setDelState(0);
			applyLeague.setApplyHandle(ApplyLeague.APPLY_HANDLE_WAITHANDLE);
			applyLeague.setApplyTime(new Date());
			if (HibernateDAO.save(applyLeague) == null ) {
				HtmlAjax.getJson(getResponse(), 1);//1代表处理失败
				return;
			}
			HtmlAjax.getJson(getResponse(), 0);//0代表成功处理
			//查询联赛管理员
			List<WXUser> adminList = leagueDAO.getAdminListByByLid(lid);
			//请求路径
			String requestURL = getRequest().getRequestURL().toString();
			//拼接完整url
			String url = requestURL.replace("apply/applyReferee", "league/leagueEdit")+"?id="+lid;
			League league = (League) HibernateDAO.findById(League.class, lid);
			//给创建人发消息
			String name = league.getName();
			templateMessage.sendApplyLeagueMes(loginUser,adminList.get(0), name, url);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), 1);//1代表处理失败
		}
	}

	/**
	 * 同意申请
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @return
	 */
	public void agreeApply() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(tid, loginUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			}
			List<Apply> list = applyDao.findApplyByUidAndTid(uid,tid);
			if (list != null && list.size() > 0) {
				Apply apply = list.get(0);
				//校验是未处理的数据
				if (!Apply.APPLY_HANDLE_WAITHANDLE.equals(apply.getApplyHandle())) {
					//非法操作
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				// 执行操作对象的map
				Map<Object, String> operates = new HashMap<Object, String>();
				//同意
				apply.setApplyHandle(Apply.APPLY_HANDLE_AGREE);
				apply.setHandleTime(new Date());
				//处理人
				apply.setHandleUser(loginUser.getId());
				operates.put(apply, "merge");
				//同意申请后建立用户和球队的关系
				UserTeam userTeam = new UserTeam();
				userTeam.setId(null);
				userTeam.setUid(uid);
				userTeam.setTid(tid);
				userTeam.setPosition(4);//成员
				operates.put(userTeam, "merge");

				//同意的成员加入球队时,可能已经发布了还没有过时的比赛和训练,
				//这时候,一旦同意,添加还没有过时的比赛和训练签到关系
				
				//比赛
				List<Race> raceIds = raceDAO.getNoTimeOutRaceIdsByTid(tid);
				if (raceIds != null && raceIds.size() > 0) {
					Date createTime = new Date();
					for (int i = 0; i < raceIds.size(); i++) {
						Sign sign = new Sign();
						sign.setTid(tid);
						sign.setUid(uid);
						sign.setIsAttendance(-1);//未签到
						sign.setSignUp(0);//设置报名状态为未报名
						sign.setRaceortrain(2);
						sign.setRaceortrainId(raceIds.get(i).getId());
						sign.setCreateTime(createTime);
						sign.setCreateUser(loginUser.getId());
						sign.setDelState(0);
						operates.put(sign, "merge");
					}
				}
				//训练
				List<Train> trainIds = trainDAO.getNoTimeOutTrainIdsByTid(tid);
				if (trainIds != null && trainIds.size() > 0) {
					Date createTime = new Date();
					for (int i = 0; i < trainIds.size(); i++) {
						Sign sign = new Sign();
						sign.setUid(uid);
						sign.setTid(tid);
						sign.setIsAttendance(-1);//未签到
						sign.setSignUp(0);//设置报名状态为未报名
						sign.setRaceortrain(1);
						sign.setRaceortrainId(trainIds.get(i).getId());
						sign.setCreateTime(createTime);
						sign.setCreateUser(loginUser.getId());
						sign.setDelState(0);
						operates.put(sign, "merge");
					}
				}
				// 0表示成功，1表示失败
				if (HibernateDAO.operateList(operates) == 1) {
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				HtmlAjax.getJson(getResponse(), true);//处理成功
				//获取队长昵称和球队名称
				Object[] cNameAndtName = teaminfoDao.getCaptainNameAndTeamNameByTid(tid);
				WXUser applyUser = (WXUser) HibernateDAO.findById(WXUser.class, uid);
				//请求路径
				String requestURL = getRequest().getRequestURL().toString();
				//拼接完整url
				String url = requestURL.replace("agreeApply", "myApply");
				templateMessage.sendSucMes(cNameAndtName[0]+"", cNameAndtName[1]+"", applyUser,url);
				// 操作日志
				String content = "同意：\"" + uid + "\"用户申请的：\"" + tid + "\"球队";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "处理申请");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//处理失败
		}
	}
	
	/**
	 * 同意申请加入联赛裁判组
	 * @author liudongxin
	 * @date 2018年3月17日
	 * @version 1.0
	 * @return
	 */
	public void agreeLeagueApply() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			List<ApplyLeague> list = applyDao.findApplyLeagueByUidAndLid(uid,lid);
			if (list != null && list.size() > 0) {
				ApplyLeague applyLeague = list.get(0);
				//校验是未处理的数据
				if (!ApplyLeague.APPLY_HANDLE_WAITHANDLE.equals(applyLeague.getApplyHandle())) {
					//非法操作
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				// 执行操作对象的map
				Map<Object, String> operates = new HashMap<Object, String>();
				//同意
				applyLeague.setApplyHandle(ApplyLeague.APPLY_HANDLE_AGREE);
				applyLeague.setHandleTime(new Date());
				applyLeague.setDelState(0);
				//处理人
				applyLeague.setHandleUser(loginUser.getId());
				operates.put(applyLeague, "merge");
				//同意申请后建立用户和联赛的关系
				ApplyLeague userLeague = new ApplyLeague();
				userLeague.setId(null);
				userLeague.setUid(uid);
				userLeague.setLid(lid);
				userLeague.setDelState(0);
				operates.put(userLeague, "merge");
				// 0表示成功，1表示失败
				if (HibernateDAO.operateList(operates) == 1) {
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				HtmlAjax.getJson(getResponse(), true);//处理成功
				//给申请人发消息
				League league = (League) HibernateDAO.findById(League.class, lid);
				User applyUser = (User) HibernateDAO.findById(User.class, uid);
				templateMessage.sendAgreeMsg(applyUser,league.getName(),loginUser, "");
				// 操作日志
				String contentlog = "同意：\"" + uid + "\"用户申请加入的：\"" + tid + "\"联赛";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", contentlog, "处理申请");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//处理失败
		}
	}
	
	/**
	 * 拒绝申请
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @return
	 */
	public void rejectApply() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(tid, loginUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			}
			List<Apply> list = applyDao.findApplyByUidAndTid(uid,tid);
			if (list != null && list.size() > 0) {
				Apply apply = list.get(0);
				//校验是未处理的数据
				if (!Apply.APPLY_HANDLE_WAITHANDLE.equals(apply.getApplyHandle())) {
					//非法操作
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				//拒绝
				apply.setApplyHandle(Apply.APPLY_HANDLE_REJECT);
				apply.setReason(reason);
				apply.setHandleTime(new Date());
				//处理人
				apply.setHandleUser(loginUser.getId());
				if (HibernateDAO.merge(apply) == null ) {
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				HtmlAjax.getJson(getResponse(), true);//处理成功
				WXUser handleUser = (WXUser) HibernateDAO.findById(WXUser.class, loginUser.getId());
				WXUser applyUser = (WXUser) HibernateDAO.findById(WXUser.class, uid);
				//请求路径
				String requestURL = getRequest().getRequestURL().toString();
				//拼接完整url
				String url = requestURL.replace("rejectApply", "myApply");
				templateMessage.sendTMes(reason, applyUser, handleUser,url);
				// 操作日志
				String content = "拒绝：\"" + uid + "\"用户申请的：\"" + tid + "\"球队";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "处理申请");
			} 
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//处理失败
		}
	}
	
	/**
	 * 拒绝申请加入联赛
	 * @author liudongxin
	 * @date 2018年3月17日
	 * @version 1.0
	 * @return
	 */
	public void rejectLeagueApply() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			List<ApplyLeague> list = applyDao.findApplyLeagueByUidAndLid(uid,lid);
			if (list != null && list.size() > 0) {
				ApplyLeague applyLeague = list.get(0);
				//校验是未处理的数据
				if (!ApplyLeague.APPLY_HANDLE_WAITHANDLE.equals(applyLeague.getApplyHandle())) {
					//非法操作
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				
				//拒绝
				applyLeague.setApplyHandle(ApplyLeague.APPLY_HANDLE_REJECT);
				applyLeague.setReason(reason);
				applyLeague.setHandleTime(new Date());
				//处理人
				applyLeague.setHandleUser(loginUser.getId());
				if (HibernateDAO.merge(applyLeague) == null ) {
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				HtmlAjax.getJson(getResponse(), true);//处理成功
				League league = (League) HibernateDAO.findById(League.class, lid);
				User applyUser = (User) HibernateDAO.findById(User.class, uid);
				templateMessage.senLeagueRefMes(reason, loginUser,applyUser, league, "");
				// 操作日志
				String contentlog = "拒绝：\"" + uid + "\"用户申请加入：\"" + lid + "\"联赛裁判组";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", contentlog, "处理申请");
			} 
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//处理失败
		}
	}
	
	/**
	 * 忽略申请
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @return
	 */
	public void ignoreApply() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(tid, loginUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			}
			List<Apply> list = applyDao.findApplyByUidAndTid(uid,tid);
			if (list != null && list.size() > 0) {
				Apply apply = list.get(0);
				//校验是未处理的数据
				if (!Apply.APPLY_HANDLE_WAITHANDLE.equals(apply.getApplyHandle())) {
					//非法操作
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				//忽略
				apply.setApplyHandle(Apply.APPLY_HANDLE_IGNORE);
				apply.setHandleTime(new Date());
				//处理人
				apply.setHandleUser(loginUser.getId());
				if (HibernateDAO.merge(apply) == null ) {
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				HtmlAjax.getJson(getResponse(), true);//处理成功
				WXUser handleUser = (WXUser) HibernateDAO.findById(WXUser.class, loginUser.getId());
				WXUser applyUser = (WXUser) HibernateDAO.findById(WXUser.class, uid);
				//请求路径
				String requestURL = getRequest().getRequestURL().toString();
				//拼接完整url
				String url = requestURL.replace("ignoreApply", "myApply");
				templateMessage.sendTMes("", applyUser, handleUser,url);
				// 操作日志
				String content = "忽略：\"" + uid + "\"用户申请的：\"" + tid + "\"球队";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "处理申请");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//处理失败
		}
	}
	
	/**
	 * 忽略申请加入联赛
	 * @author liudongxin
	 * @date 2018年3月17日
	 * @version 1.0
	 * @return
	 */
	
	public void ignoreLeagueApply() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			List<ApplyLeague> list = applyDao.findApplyLeagueByUidAndLid(uid,lid);
			if (list != null && list.size() > 0) {
				ApplyLeague applyLeague = list.get(0);
				//校验是未处理的数据
				if (!ApplyLeague.APPLY_HANDLE_WAITHANDLE.equals(applyLeague.getApplyHandle())) {
					//非法操作
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				//忽略
				applyLeague.setApplyHandle(ApplyLeague.APPLY_HANDLE_IGNORE);
				applyLeague.setHandleTime(new Date());
				//处理人
				applyLeague.setHandleUser(loginUser.getId());
				if (HibernateDAO.merge(applyLeague) == null ) {
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				HtmlAjax.getJson(getResponse(), true);//处理成功
				League league = (League) HibernateDAO.findById(League.class, lid);
				User applyUser = (User) HibernateDAO.findById(User.class, uid);
				templateMessage.senLeagueRefMes(reason, loginUser,applyUser, league, "");
				// 操作日志
				String contentlog = "忽略：\"" + uid + "\"用户申请加入：\"" + lid + "\"联赛裁判组";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", contentlog, "处理申请");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//处理失败
		}
	}
	
	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}
	
}
