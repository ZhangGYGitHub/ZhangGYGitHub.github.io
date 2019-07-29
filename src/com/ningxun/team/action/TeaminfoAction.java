package com.ningxun.team.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.apply.dao.ApplyDao;
import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.file.ImgCompress;
import com.ningxun.position.dao.PositionDao;
import com.ningxun.position.vo.Position;
import com.ningxun.race.dao.RaceDAO;
import com.ningxun.race.vo.Race;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.sign.dao.signDao;
import com.ningxun.sign.vo.Sign;
import com.ningxun.team.dao.PictureDao;
import com.ningxun.team.dao.TeaminfoDao;
import com.ningxun.team.dao.UserTeamDao;
import com.ningxun.team.vo.Picture;
import com.ningxun.team.vo.Teaminfo;
import com.ningxun.team.vo.UserTeam;
import com.ningxun.tools.Config;
import com.ningxun.tools.ShareUtil;
import com.ningxun.train.dao.TrainDAO;
import com.ningxun.train.vo.Train;
import com.ningxun.util.HtmlAjax;
import com.ningxun.wxuserinfo.dao.LocationDAO;
import com.ningxun.wxuserinfo.vo.Location;
import com.ningxun.wxuserinfo.vo.WXUser;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TeaminfoAction extends BaseSupportAction {
	
	private static final long serialVersionUID = 1L;
	private TeaminfoDao teaminfoDao = new TeaminfoDao();
	private UserTeamDao userTeamDao = new UserTeamDao();
	private PositionDao positionDao = new PositionDao();
	private PictureDao pictureDao = new PictureDao();
	private LocationDAO locationDao = new LocationDAO();
	private RaceDAO raceDAO = new RaceDAO();
	private TrainDAO trainDAO = new TrainDAO();
	private ApplyDao applyDao = new ApplyDao();
	private signDao signDao = new signDao();
	private HtmlAjax htmlAjax = new HtmlAjax();
	public Teaminfo teaminfo = new Teaminfo();
	private Log log = LogFactory.getLog(getClass());
	private String teamName;//搜索的球队名称
	private Integer uid;//用户id
	private String clothesNum;//球衣号码
	private Integer position;//职位
	private String remakeName;//备注名
	private String playerPosition;//所踢位置
	
	/**
	 * 上拉加载更多
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	public void ajaxLoadMoreList() throws Exception {
		try {
			String sql = "";
			if (teamName != null && teamName.trim().length() > 0) {
				//查询
				sql = teaminfoDao.getQueryTeaminfoListSql(teamName.trim());
			} else {
				//推荐
				//获取当前登录人
				WXUserSS loginUser = (WXUserSS) getUser();
				//查询出登录人的详细信息
				WXUser tempUser = (WXUser) HibernateDAO.findById(WXUser.class,loginUser.getId());
				sql = teaminfoDao.getRecommenderTeaminfoListSql(tempUser.getId(),tempUser.getProvince(),tempUser.getCity());
			}
			
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, teaminfoDao.getTagName(), pageNo, 0);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 跳转搜索球队页面
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @return
	 */
	public String toQueryTeam() throws Exception{
		try {
			//先根据用户地址推荐10个不属于自己的球队
			//获取当前登录人
			WXUserSS loginUser = (WXUserSS) getUser();
			//查询出登录人的详细信息
			WXUser tempUser = (WXUser) HibernateDAO.findById(WXUser.class,loginUser.getId());
			List<Object[]> list = teaminfoDao.recommenderTeaminfoList(tempUser.getId(),tempUser.getProvince(),tempUser.getCity(),1,PAGE_SIZE);
			if (list == null || list.size() == 0) {
				//根据位置没有查询出数据
				//随机取出用户木有加入过的球队的3条数据
				list = teaminfoDao.getTeaminfoList(tempUser.getId(),1,3);
			}
			ActionContext.getContext().put("list", list);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 获取当前登录人的所有球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @return
	 */
	public String teaminfoListByUid() {
		try {
			//当前登录用户
			WXUserSS loginUser = (WXUserSS) getUser();
			if (loginUser == null) {
				//没有登录
				addActionMessage("对不起，没有登录不能查看球队！");
				return ERROR;
			}
			//我创建的球队
			List<Object[]> createTeams = teaminfoDao.getCreateTeamByUid(loginUser.getId());
			//我管理的球队
			List<Object[]> manageTeams = teaminfoDao.getManageTeamByUid(loginUser.getId());
			//我加入的球队
			List<Object[]> joinTeams = teaminfoDao.getJoinTeamByUid(loginUser.getId());
			ActionContext.getContext().put("createTeams", createTeams);
			ActionContext.getContext().put("manageTeams", manageTeams);
			ActionContext.getContext().put("joinTeams", joinTeams);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}

	public String teaminfoAdd() {
		try {
			if (teaminfo.getId() != null) {
				teaminfo = (Teaminfo) HibernateDAO.findById(Teaminfo.class, teaminfo.getId());
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 球队管理
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @return
	 */
	public String teaminfoManage() {
		try {
			//当前登录人
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(teaminfo.getId(), loginUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				//跳转提示被移除页面
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			}
			ActionContext.getContext().put("position", position);
			teaminfo = (Teaminfo) HibernateDAO.findById(Teaminfo.class, teaminfo.getId());
			ActionContext.getContext().put("currentUserId", loginUser.getId());
			
			//常踢位置列表
			List<Location> locations = locationDao.getList();
			if (locations != null && locations.size()>0) {
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < locations.size(); i++) {
					JSONObject jsonObject2 = new JSONObject();
					jsonObject2.put("title", locations.get(i).getLocationName());
					jsonObject2.put("value", locations.get(i).getId());
					jsonArray.add(jsonObject2);
				}
				//System.out.println(jsonArray);
				ActionContext.getContext().put("locations", jsonArray.toString());
			}
			if (!"队员".equals(position.getPositionName())) {
				//待处理列表
				List<WXUser> waitHandleList = applyDao.getApplyWaitHandleList(teaminfo.getId());
				ActionContext.getContext().put("waitHandleList", waitHandleList);
			}
			//登录用户的常踢位置
			UserTeam myLocation = userTeamDao.getPlayerPositionByUid(loginUser.getId(),teaminfo.getId());
			ActionContext.getContext().put("myLocation", myLocation);
			//携带分享的数据到页面
			String appId = Config.getAppid();
			//时间戳
			String timestamp = ShareUtil.getTimeStamp();
			//随机字符串
			String nonceStr = ShareUtil.getNonceStr();
			//请求路径
			String requestURL = getRequest().getRequestURL().toString();
			//拼接完整url
			String url = requestURL+"?teaminfo.id="+teaminfo.getId();
			//签名
			String signature = ShareUtil.getSignature(nonceStr,timestamp,url);
			ActionContext.getContext().put("appId", appId);
			ActionContext.getContext().put("timestamp", timestamp);
			ActionContext.getContext().put("nonceStr", nonceStr);
			ActionContext.getContext().put("signature", signature);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 退出球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @return
	 */
	public String quitTeam() {
		try {
			//获取当前登录人
			WXUserSS loginUser = (WXUserSS) getUser();
			userTeamDao.quitTeamByUid(teaminfo.getId(),loginUser.getId());
			//删除申请处理同意记录,以便多次申请
			applyDao.deleteApplyRecord(loginUser.getId(),teaminfo.getId());
			//删除所有的签到信息
			List<String> sqlList = signDao.getDeleteSignListSqlByTid(loginUser.getId(),teaminfo.getId());
			if (sqlList != null && sqlList.size() > 0) {
				HibernateDAO.executeSqlList(sqlList);
			}
			// 操作日志
			String content = "退出编号为：\"" + teaminfo.getId() + "\"的球队";
			AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "退出");
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 移除球队成员
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @return
	 */
	public void removeMember() throws Exception{
		try {
			//不能移除自己
			//如果操作用户是自己,页面已经没有显示删除用户按钮,若出现,则说明非法操作
			//获取当前登录用户
			WXUserSS currentUser = (WXUserSS) getUser();
			if (uid.equals(currentUser.getId())) {
				//非法操作
				HtmlAjax.getJson(getResponse(), false);//移除失败
				return;
			}
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(teaminfo.getId(), currentUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			}
			userTeamDao.removeMemberByUid(uid,teaminfo.getId());
			//删除申请处理同意记录,以便多次申请
			applyDao.deleteApplyRecord(uid,teaminfo.getId());
			//删除所有的签到信息
			List<String> sqlList = signDao.getDeleteSignListSqlByTid(uid,teaminfo.getId());
			if (sqlList != null && sqlList.size() > 0) {
				HibernateDAO.executeSqlList(sqlList);
			}
			HtmlAjax.getJson(getResponse(), true);//移除成功
			// 操作日志
			String content = "移除：\"" + teaminfo.getId() + "\"球队的：\"" + uid + "\"成员";
			AddLog.addOperateLog(String.valueOf(currentUser.getId()), currentUser.getNickname(), "", content, "移出");
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//移除失败
		}
	}
	
	/**
	 * 解散球队
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @return
	 */
	public String teaminfoDissolve() {
		try {
			//删除签到信息的执行逻辑必须放在最前面,因为删除签到信息的时候还需要查询球队的成员列表
			//删除所有成员和球队的签到信息
			List<String> sqlList = signDao.getDeleteSignListSqlByTid(teaminfo.getId());
			if (sqlList != null && sqlList.size() > 0) {
				HibernateDAO.executeSqlList(sqlList);
			}
			//删除球队
			teaminfo = (Teaminfo) HibernateDAO.findById(Teaminfo.class, teaminfo.getId());
			WXUserSS currentUser = (WXUserSS) getUser();
			teaminfo.setDeleteUser(currentUser.getId());
			teaminfo.setDeleteTime(new Date());
			teaminfo.setDelState(1);
			if (HibernateDAO.merge(teaminfo) == null) {
				addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
				return ERROR;
			}
			//删除球队和用户的关联关系
			userTeamDao.deleteMemberListByTid(teaminfo.getId());
			String content = "解散-编号为：\"" + teaminfo.getId() + "\"的球队";
			AddLog.addOperateLog(String.valueOf(currentUser.getId()), currentUser.getNickname(), "", content, "解散");
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}

	public String teaminfoDetail(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			Object[] obj = teaminfoDao.getTeaminfoDetail(teaminfo.getId());
			//关联查询全家福
			Picture picture = pictureDao.getPictureByTid(teaminfo.getId());
			if (obj != null) {
				ActionContext.getContext().put("obj", obj);
				ActionContext.getContext().put("picture", picture);
			}
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(teaminfo.getId(), loginUser.getId());
			if (position != null) {
				ActionContext.getContext().put("position", position);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	public void teaminfoSave() throws Exception {
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			if (teaminfo.getId() == null) {
				//添加
				//防止重复添加，不允许同一个人创建同名的球队
				boolean check = teaminfoDao.checkTeamName(0,loginUser.getId(), teaminfo.getName().trim());
				if (check) {
					//已经存在，说明在发重复请求
					//发送重复添加请求说明添加成功,什么都不做，直接return
					return;
				}
				teaminfo.setName(teaminfo.getName().trim());
				//创建球队则为队长
				teaminfo.setCaptainId(loginUser.getId());
				teaminfo.setCreateUser(loginUser.getId());
				teaminfo.setCreateTime(new Date());
				teaminfo.setDelState(0);
				if (HibernateDAO.save(teaminfo) == null) {
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				//建立用户球队关系
				UserTeam userTeam = new UserTeam();
				userTeam.setId(null);
				userTeam.setUid(loginUser.getId());
				userTeam.setTid(teaminfo.getId());
				userTeam.setPosition(1);//队长
				if (HibernateDAO.save(userTeam) == null) {
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				
				HtmlAjax.getJson(getResponse(), true);//成功
				// 操作日志
				String content = "添加-编号为：\"" + teaminfo.getId() + "\"的球队";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "添加");
				
			} else {
				//编辑
				//获取职位
				Position position = positionDao.queryPositionByUidAndTid(teaminfo.getId(), loginUser.getId());
				if (position == null ) {
					//已经不是该球队成员,说明该用户已经被移除球队了
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				} else if ("队员".equals(position.getPositionName())) {
					//已经不是管理员,不再具备该操作
					HtmlAjax.getJson(getResponse(), false);//处理失败
					return;
				}
				teaminfo.setName(teaminfo.getName().trim());
				teaminfo.setModifyUser(loginUser.getId());
				teaminfo.setModifyTime(new Date());
				teaminfo.setDelState(0);
				if (HibernateDAO.merge(teaminfo) == null) {
					HtmlAjax.getJson(getResponse(), false);//失败
					return;
				}
				HtmlAjax.getJson(getResponse(), true);//成功
				// 操作日志
				String content = "修改-编号为：\"" + teaminfo.getId() + "\"的球队";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "修改");
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//失败
		}
	}

	/**
	 * 球队成员列表
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @return
	 */
	public String teaminfoMember() {
		try {
			List<Object[]> users = teaminfoDao.getMemberListByTid(teaminfo.getId());
			//放在最开始，因为后面移除了队长
			ActionContext.getContext().put("userNum", users.size());
			//把队长单独拿出来,因为不能移除队长和变更队长的职位
			if (users != null && users.size() > 0) {
				//按职位排序，队长为第一个
				Object[] obj = users.get(0);
				ActionContext.getContext().put("obj", obj);
				//移除
				users.remove(0);
			}
			ActionContext.getContext().put("users", users);
			//当前登录
			WXUserSS loginUser = (WXUserSS) getUser();
			ActionContext.getContext().put("currentUserId", loginUser.getId());
			//获取操作人的职位
			Position position = positionDao.queryPositionByUidAndTid(teaminfo.getId(), loginUser.getId());
			if (position != null) {
				ActionContext.getContext().put("position", position);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 更改职位
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @return
	 */
	public void changePosition() throws Exception{
		try {
			userTeamDao.changePositionByUidAndTid(uid,teaminfo.getId(),position);
			HtmlAjax.getJson(getResponse(), true);//修改成功
			WXUserSS currentUser = (WXUserSS) getUser();
			String content = "修改：\"" + uid + "\"用户在：\"" + teaminfo.getId() + "\"球队的职位";
			AddLog.addOperateLog(String.valueOf(currentUser.getId()), currentUser.getNickname(), "", content, "修改职位");
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//修改成功
		}
	}
	
	/**
	 * 更改球衣号码
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	public void changeClothesNum() throws Exception {
		try {
			userTeamDao.changeClothesNumByUidAndTid(uid,teaminfo.getId(),clothesNum);
			HtmlAjax.getJson(getResponse(), true);//修改成功
			WXUserSS currentUser = (WXUserSS) getUser();
			String content = "修改：\"" + uid + "\"用户在：\"" + teaminfo.getId() + "\"球队的秋衣号码";
			AddLog.addOperateLog(String.valueOf(currentUser.getId()), currentUser.getNickname(), "", content, "修改球衣号码");
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//修改失败
		}
	}
	
	/**
	 * 异步上传队徽图片
	 * @author hujian
	 * @date 2017年7月23日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public void ajaxUploadPic() throws Exception{
		JSONObject jsonObject = new JSONObject();
		try {
			int statusCode = saveFile(Config.getFileUploadPath());
			boolean success = false;
			//0：上传成功；1:没有上传文件；2：上传异常 
			if (statusCode == 0) {
				//压缩图片
				ImgCompress imgCompress = new ImgCompress(upload.get(0), uploadFiles.get(0).getUploadFileName(), Config.getFileUploadPath());
				String oldName = uploadFiles.get(0).getUploadFileName();
				String newName = "max_" + imgCompress.getPicName();
				success = true;
				//{"newName":"", "oldName":"", "success":}
				jsonObject.put("newName", newName);
				jsonObject.put("oldName", oldName);
			} else {
				success = false;
			}
			jsonObject.put("success", success);
			HtmlAjax.getJson(getResponse(),jsonObject);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), jsonObject);
		}
	}
	
	/**
	 * 异步上传全家福
	 * @author hujian
	 * @date 2017年7月23日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public void uploadPicture() throws Exception{
		JSONObject jsonObject = new JSONObject();
		try {
			WXUserSS currentUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(teaminfo.getId(), currentUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				jsonObject.put("success", false);
				HtmlAjax.getJson(getResponse(), jsonObject);//失败
				return;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				jsonObject.put("success", false);
				HtmlAjax.getJson(getResponse(), jsonObject);//失败
				return;
			}
			int statusCode = saveFile(Config.getFileUploadPath());
			boolean success = false;
			//0：上传成功；1:没有上传文件；2：上传异常 
			if (statusCode == 0) {
				//压缩图片
				ImgCompress imgCompress = new ImgCompress(upload.get(0), uploadFiles.get(0).getUploadFileName(), Config.getFileUploadPath());
				String oldName = uploadFiles.get(0).getUploadFileName();
				String newName = "max_" + imgCompress.getPicName();
				success = true;
				//{"newName":"", "success":}
				jsonObject.put("newName", newName);
				//这里上传全家福是替换，若没有上传过则添加
				//先查询
				Picture temp = pictureDao.getPictureByTid(teaminfo.getId());
				if (temp == null ) {
					temp = new Picture();
					//没有则添加
					temp.setTid(teaminfo.getId());
					temp.setPicNewName(newName);
					temp.setPicOldName(oldName);
					temp.setCreateTime(new Date());
					temp.setCreateUser(currentUser.getId());
					temp.setDelState(0);
					if (HibernateDAO.save(temp) == null) {
						success = false; //异常
					}
				} else {
					//这里是替换
					temp.setPicNewName(newName);
					temp.setPicOldName(oldName);
					temp.setModifyTime(new Date());
					temp.setModifyUser(currentUser.getId());
					if (HibernateDAO.merge(temp) == null) {
						success = false; //异常
					}
				}
			} else {
				success = false; //异常
			}
			jsonObject.put("success", success);
			HtmlAjax.getJson(getResponse(), jsonObject);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), jsonObject);
		}
	}
	
	/**
	 * 跳转上传全家福页面
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @return
	 */
	public String teamPicture() {
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(teaminfo.getId(), loginUser.getId());
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
			//根据球队id先查询已有的全家福
			Picture picture = pictureDao.getPictureByTid(teaminfo.getId());
			ActionContext.getContext().put("picture", picture);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 异步校验球队名称
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public void checkTeamName() throws Exception{
		boolean falg = false;
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			// true 存在  ,  false 不存在
			falg = teaminfoDao.checkTeamName(teaminfo.getId(),loginUser.getId(),teaminfo.getName().trim());
			HtmlAjax.getJson(getResponse(), falg);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), falg);//修改成功
		}
	}
	
	/**
	 * 修改用户在球队的备注名
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public void changeRemakeName() throws Exception{
		try {
			userTeamDao.changeRemakeName(uid,teaminfo.getId(),remakeName.trim());
			HtmlAjax.getJson(getResponse(), true);
			// 操作日志
			WXUserSS currentUser = (WXUserSS) getUser();
			String content = "修改：\"" + teaminfo.getId() + "\"球队的：\"" + uid + "\"用户的备注名";
			AddLog.addOperateLog(String.valueOf(currentUser.getId()), currentUser.getNickname(), "", content, "修改备注");
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//修改成功
		}
	}
	
	/**
	 * 修改用户在球队的踢球位置
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public void changePlayerPosition() throws Exception{
		try {
			WXUserSS currentUser = (WXUserSS) getUser();
			userTeamDao.changePlayerPosition(currentUser.getId(),teaminfo.getId(),playerPosition.trim());
			HtmlAjax.getJson(getResponse(), true);
			// 操作日志
			String content = "修改：\"" + currentUser.getId() + "\"用户在：\"" + teaminfo.getId() + "\"球队的所踢位置";
			AddLog.addOperateLog(String.valueOf(currentUser.getId()), currentUser.getNickname(), "", content, "修改常踢位置");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//修改成功
		}
	}
	
	/**
	 * 分享链接
	 * @author hujian
	 * @date 2017年8月8日
	 * @version 1.0
	 * @throws Exception
	 */
	public String shareUrl() throws Exception{
		try {
			//String requestURL = getRequest().getRequestURL().toString();
			//System.out.println(requestURL);
			//如果没有登录跳转登录
			WXUserSS loginUser = (WXUserSS) getUser();
			if (loginUser == null) {
				//没有登录
				return LOGIN;
			}
			//查看是否已经加入球队了
			Position loginUserPosition = positionDao.queryPositionByUidAndTid(teaminfo.getId(), loginUser.getId());
			if (loginUserPosition != null ){
				//已经加入球队
				return "toIndex";
			} 
			//登录，直接跳转申请加入球队页面
			//获取邀请人职位
			Position position = positionDao.queryPositionByUidAndTid(teaminfo.getId(), uid);
			ActionContext.getContext().put("position", position);
			//携带分享球队的信息
			Object[] obj = teaminfoDao.getTeaminfoDetail(teaminfo.getId());
			ActionContext.getContext().put("obj", obj);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);// 记录错误日志
			return ERROR;
		}
	}
	
	/**
	 * 分享邀请确认加入球队
	 * @author hujian
	 * @date 2017年8月9日
	 * @version 1.0
	 * @return
	 */
	
	public String confirmJoin(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//先判断是否已经确认加入
			boolean flag = userTeamDao.checkUserIsTeamMember(loginUser.getId(), teaminfo.getId());
			if (flag) {
				//已经加入了,说明用户重复点击分享链接进行确认加入
				return SUCCESS;
			}
			// 执行操作对象的map
			Map<Object, String> operates = new HashMap<Object, String>();
			
			//建立用户和球队的关系
			UserTeam userTeam = new UserTeam();
			userTeam.setId(null);
			userTeam.setUid(loginUser.getId());
			userTeam.setTid(teaminfo.getId());
			userTeam.setPosition(4);//成员
			operates.put(userTeam, "merge");
			
			//同意加入球队时,可能已经发布了还没有过时的比赛和训练,
			//这时候,一旦同意,添加还没有过时的比赛和训练签到关系
			//比赛
			List<Race> raceIds = raceDAO.getNoTimeOutRaceIdsByTid(teaminfo.getId());
			if (raceIds != null && raceIds.size() > 0) {
				Date createTime = new Date();
				for (int i = 0; i < raceIds.size(); i++) {
					Sign sign = new Sign();
					sign.setUid(loginUser.getId());
					sign.setTid(teaminfo.getId());
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
			List<Train> trainIds = trainDAO.getNoTimeOutTrainIdsByTid(teaminfo.getId());
			if (trainIds != null && trainIds.size() > 0) {
				Date createTime = new Date();
				for (int i = 0; i < trainIds.size(); i++) {
					Sign sign = new Sign();
					sign.setUid(loginUser.getId());
					sign.setTid(teaminfo.getId());
					sign.setIsAttendance(0);//未签到
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
				addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
				return ERROR;
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 后台校验
	 * @author hujian
	 * @date 2017年7月31日
	 * @version 1.0
	 */
	public void validateTeaminfoSave() {
		Boolean haveValidate = true;
		if (teaminfo.getName().length() < 2 || teaminfo.getName().length() > 16) {
			haveValidate = false;
		}
		if (teaminfo.getTeamDeclaration().length() < 2 || teaminfo.getTeamDeclaration().length() > 12) {
			haveValidate = false;
		}
		if (teaminfo.getIntroduce() != null && teaminfo.getIntroduce().length() > 200) {
			haveValidate = false;
		}
		if (teaminfo.getSponsor() != null && teaminfo.getSponsor().length() > 50) {
			haveValidate = false;
		}
		if (teaminfo.getRegulations() != null && teaminfo.getRegulations().length() > 500) {
			haveValidate = false;
		}
		
		if (haveValidate) {
			return;
		}

	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getClothesNum() {
		return clothesNum;
	}

	public void setClothesNum(String clothesNum) {
		this.clothesNum = clothesNum;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getRemakeName() {
		return remakeName;
	}

	public void setRemakeName(String remakeName) {
		this.remakeName = remakeName;
	}

	public String getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(String playerPosition) {
		this.playerPosition = playerPosition;
	}
	
}
