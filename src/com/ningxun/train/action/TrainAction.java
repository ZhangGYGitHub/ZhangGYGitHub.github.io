package com.ningxun.train.action;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.ningxun.security.action.WXUserSS;
import com.ningxun.sign.dao.signDao;
import com.ningxun.sign.vo.Sign;
import com.ningxun.team.vo.UserTeam;
import com.ningxun.train.dao.TrainDAO;
import com.ningxun.train.vo.Train;
import com.ningxun.train.vo.TrainCustom;
import com.ningxun.util.HtmlAjax;
import com.ningxun.util.ValidateUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: TrainAction.java<br/>
 *<li>创建时间: 2017-7-20 上午9:29:03<br/>
 *
 *@author 高佳伟
 *@version [v1.00]
 */

public class TrainAction extends BaseSupportAction {
	

	private List<Train> trainList;//训练列表集合
	private List<UserTeam> userList;//用户球队关联列表集合
	private List<Object[]> leaveList;//请假列表集合
	private List signUpList;//报名列表集合
	private List signlist;//签到人员列表
	private List<Object[]> unSignList;

	private Train train;//训练
	private Sign sign;//签到报名
	private Leave leave;//请假
	private Integer teamId;//球队id
	private Integer id;//训练id
	private String picker1;//开始时间
	private String picker2;//结束时间
	
	
	private Log log = LogFactory.getLog(getClass());//日志
	private TrainDAO trainDAO = new TrainDAO();//训练
	private LeaveDAO leaveDAO = new LeaveDAO();//请假
	private signDao signDAO = new signDao();//签到报名
	private PositionDao positionDao = new PositionDao();//职位
	private HtmlAjax htmlAjax = new HtmlAjax();//ajax
	
	/**
	* 描述:展示训练列表<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-20/上午11:20:18<br/>
	* @author 高佳伟  
	* @version V1.0 
	* @throws Exception
	*/
	public String showTrainList() throws Exception{
		try {
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			if (wxUserSS == null) {
				//没有登录
				addActionMessage("对不起，没有登录不能查看比赛！");
				return ERROR;
			}
			ActionContext.getContext().put("currentUserId", wxUserSS.getId());
			
			List<Object[]> list = trainDAO.findTrainListByTid(teamId,pageNo,PAGE_SIZE);
			List<TrainCustom> trains = new ArrayList<TrainCustom>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] temp = list.get(i);
					//tr.id,tr.trainName,tr.trainPlace,tr.startTime,tr.endTime,tr.isSign,tr.signTime,tr.isRelease,tr.createUser,tr.tid,number
					TrainCustom tc = new TrainCustom();
					tc.setId((Integer) temp[0]);
					tc.setTrainName(temp[1]+"");
					tc.setTrainPlace(temp[2]+"");
					tc.setStartTime((Date) temp[3]);
					tc.setEndTime((Date) temp[4]);
					if (temp[5] != null ) {
						tc.setIssign((Integer) temp[5]);
					}
					if (temp[6] != null ) {
						tc.setSignTime((Integer) temp[6]);
					}
					tc.setIsRelease((Integer) temp[7]);
					tc.setCreateUser((Integer) temp[8]);
					tc.setTid((Integer) temp[9]);
					tc.setNumber(((BigInteger) temp[10]).intValue());
					//添加到集合
					trains.add(tc);
				}
				ActionContext.getContext().put("trains", trains);
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
	 * 上拉加载更多已经处理的列表
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	public void ajaxLoadMoreTrainList() throws Exception {
		try {
			String sql = trainDAO.getTrainListSql(teamId);
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, trainDAO.getTagName(), pageNo, 0);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	* 描述: 添加或编辑训练<br/>
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
				train = (Train) HibernateDAO.findById(Train.class, id);
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
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
			train = (Train) HibernateDAO.findById(Train.class, id);
			}
			//获取当前用户信息
			WXUserSS wxUserSS = (WXUserSS) getUser();
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
	* 描述: 详情展示(模板消息)<br/>
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
				train = (Train) HibernateDAO.findById(Train.class, id);
				if (train != null) {
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
	public void saveTrain() throws Exception{
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
				train.setStartTime(sdf.parse(picker1));
			}
			if(picker2 != null && picker2.length() >0){
				train.setEndTime(sdf.parse(picker2));
			}
			if (train.getId()==null) {
				//添加
				train.setCreateTime(new Date());
				train.setCreateUser(wxUserSS.getId());
				train.setTid(teamId);
				train.setDelState(0);
				Integer saveId = (Integer) HibernateDAO.save(train);
				
				//在签到表中逐条存入trainId和uid
				userList = trainDAO.findUserList(teamId);
				List<Object> signList = new ArrayList<Object>();
				Date creatTimeDate = null;
				if (train.getSignTime() != null && train.getSignTime() == 0) {
					creatTimeDate = new Date(train.getStartTime().getTime()-30*60*1000);
				}else if (train.getSignTime() != null && train.getSignTime() == 1) {
					creatTimeDate = new Date(train.getStartTime().getTime()-60*60*1000);
				}else if (train.getSignTime() != null && train.getSignTime() == 2) {
					creatTimeDate = new Date(train.getStartTime().getTime()-120*60*1000);
				}
				for (int i = 0; i < userList.size(); i++) {
					//利用构造函数初始化签到对象
					Sign sign = new Sign(null,userList.get(i).getUid(),teamId,1,train.getId(),null,0,0,creatTimeDate,wxUserSS.getId(),null,null,null,null,0);
					signList.add(sign);
				}
				HibernateDAO.mergeList(signList);
				
				
				if (saveId != null) {
					HtmlAjax.getJson(getResponse(), true);//成功
					//记录日志
					String content = "添加-编号为：\"" + train.getId() + "\"的训练信息";
					AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "添加");
				}else {
					HtmlAjax.getJson(getResponse(), false);//失败
					return;
				}
			}else{
				//修改
				train.setModifyTime(new Date());
				train.setModifyUser(wxUserSS.getId());
				HibernateDAO.update(train);
				HtmlAjax.getJson(getResponse(), true);//成功
				//记录日志
				String content = "修改-编号为：\"" + train.getId() + "\"的训练信息";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "修改");
				}
			if(train.getIsRelease()==1){
				id = train.getId(); 
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
	 *         创建时间：2017-7-20/上午11:25:08<br/>
	 * @author 高佳伟
	 * @version V1.0
	 */
	public String Delete() {
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
			//查询被删除对象
			train = (Train) HibernateDAO.findById(Train.class, id);
			//添加删除信息
			train.setDeleteTime(new Date());
			train.setDeleteUser(wxUserSS.getId());
			train.setDelState(1);
			HibernateDAO.update(train);
			//记录日志
			String content = "删除-编号为：\"" + train.getId() + "\"的训练信息";
			AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "删除");
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
	* 创建时间：2017-7-28/下午4:10:29<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
	*/
	public void signUp() throws Exception{
		try {
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			//获取当前登录人的职位
			Position position = positionDao.queryPositionByUidAndTid(teamId, wxUserSS.getId());
			if (position == null) {//若职位为空则被移除
				HtmlAjax.getJson(getResponse(), 2);//失败
				return;
			}
			//ajax检验是否比赛逾期
			boolean flag = trainDAO.checkTimeOutByRid(id);
			if(!flag){
				// 训练未结束
				sign = signDAO.findSignUpList(id, wxUserSS.getId(), 1);
				if(sign != null && sign.getSignUp() == 1){
					HtmlAjax.getJson(getResponse(), 3);//已经报名
					return;
				}	
				sign.setSignUp(1);
				HibernateDAO.update(sign);
				
				//修改请假状态
				leave = leaveDAO.findLeaveList(id, wxUserSS.getId(), 1);
				if (leave != null) {//此人已请过假
					HibernateDAO.delete(leave);
				}
				
				HtmlAjax.getJson(getResponse(), 1);//成功
				//记录日志
				String content = "报名-编号为：\"" + id + "\"的训练";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()),wxUserSS.getNickname(), "", content, "报名");
			} else {
				// 训练已结束
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
	* 创建时间：2017-7-28/下午4:13:16<br/>
	* @author 高佳伟  
	* @version V1.0  
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
			action.saveRaceOrTrain(id, teamId, wxUserSS.getNickname(), 2);
			train = (Train) HibernateDAO.findById(Train.class, id);
			//未发布过，更改发布状态
			if (train.getIsRelease()!=1) {
				train.setIsRelease(1);
				HibernateDAO.update(train);
				//记录日志
				String content = "发布-编号为：\"" + train.getId() + "\"的训练信息";
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
	* 创建时间：2017-7-28/下午4:14:29<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
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
			boolean flag = trainDAO.checkTimeOutByRid(id);
			if(!flag){
				//训练未结束
				leave = leaveDAO.findLeaveList(id, wxUserSS.getId(), 1);
				if(leave != null){
					HtmlAjax.getJson(getResponse(),3);//已经请过假
				}else{
					HtmlAjax.getJson(getResponse(),1);//可以请假
				}
				return NONE;
			}else {
				HtmlAjax.getJson(getResponse(),4);//训练已结束
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
			boolean flag = trainDAO.checkTimeOutByRid(id);
			HtmlAjax.getJson(getResponse(), flag);
		} catch (Exception e) {
			HtmlAjax.getJson(getResponse(), true);
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
			train = (Train) HibernateDAO.findById(Train.class, id);
			leaveList = leaveDAO.getLeaveList(id, 1,teamId);
			Integer leaveNum =0;
			for (int i = 0; i < leaveList.size(); i++) {
				if ( (Integer)leaveList.get(i)[6] != 1 && (Integer)leaveList.get(i)[6] != 2) {
					leaveNum++;
				}
				
			}
			ActionContext.getContext().put("leaveNum", leaveNum);
			signUpList= signDAO.getSignUpList(id, 1,teamId);
			signlist = signDAO.findSYsignList(id, teamId, 1);
			unSignList = signDAO.findUnSignList(1, train.getId());
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
	* 描述: 后台校验<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-10/上午10:48:02<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public void validateRaceSave() {
		Boolean haveValidate = true;
		//训练名称
		Object checkProperty = train.getTrainName();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "trainName", "训练名称", 20, true)) {
			haveValidate = false;
		}
		
		//开始时间
		checkProperty = train.getStartTime();
		if (!ValidateUtil.submitCheck(this, "时间", checkProperty, "startTime", "开始时间",0, true)) {
			haveValidate = false;
		}
		
		//结束时间
		checkProperty = train.getEndTime();
		if (!ValidateUtil.submitCheck(this, "时间", checkProperty, "endTime", "结束时间",0, true)) {
			haveValidate = false;
		}
		
		//比赛地点
		checkProperty = train.getTrainPlace();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "trainPlace", "训练地点", 20, true)) {
			haveValidate = false;
		}

		//训练内容
		checkProperty = train.getTrainContent();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "trainContent", "训练内容", 500, false)) {
			haveValidate = false;
		}
		
		if (haveValidate) {
			return;
		}
		
	}
	
	
	public List<Train> getTrainList() {
		return trainList;
	}
	
	public void setTrainList(List<Train> trainList) {
		this.trainList = trainList;
	}
	
	public Train getTrain() {
		return train;
	}
	
	public List getSignlist() {
		return signlist;
	}

	public void setSignlist(List signlist) {
		this.signlist = signlist;
	}

	public void setTrain(Train train) {
		this.train = train;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	

	public List<Object[]> getLeaveList() {
		return leaveList;
	}

	public void setLeaveList(List<Object[]> leaveList) {
		this.leaveList = leaveList;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	public List getSignUpList() {
		return signUpList;
	}

	public void setSignUpList(List signUpList) {
		this.signUpList = signUpList;
	}

	public String getPicker1() {
		return picker1;
	}
	
	public void setPicker1(String picker1) {
		this.picker1 = picker1;
	}
	
	public String getPicker2() {
		return picker2;
	}
	
	public void setPicker2(String picker2) {
		this.picker2 = picker2;
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

	public List<Object[]> getUnSignList() {
		return unSignList;
	}

	public void setUnSignList(List<Object[]> unSignList) {
		this.unSignList = unSignList;
	}
}

