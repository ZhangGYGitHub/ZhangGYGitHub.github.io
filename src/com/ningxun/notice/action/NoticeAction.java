package com.ningxun.notice.action;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.file.ImgCompress;
import com.ningxun.league.vo.League;
import com.ningxun.notice.dao.NoticeDao;
import com.ningxun.notice.vo.Notice;
import com.ningxun.notice.vo.NoticeTeam;
import com.ningxun.position.dao.PositionDao;
import com.ningxun.position.vo.Position;
import com.ningxun.race.vo.Race;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.team.dao.TeaminfoDao;
import com.ningxun.team.vo.UserTeam;
import com.ningxun.tools.Config;
import com.ningxun.tools.TemplateMessage;
import com.ningxun.train.vo.Train;
import com.ningxun.util.HtmlAjax;
import com.opensymphony.xwork2.ActionContext;


/**
* <li>@ClassName: NoticeAction<br/>
* <li>创建时间：2017年7月22日 下午7:54:10<br/>
*
* 描述:这里用一句话描述这个类的作用<br/>
* @author 梦强
*/
public class NoticeAction extends BaseSupportAction {
	
	//通知集合
	private List list;
	private Notice notice = new Notice();
	private NoticeDao noticeDao = new NoticeDao();
	private PositionDao positionDao = new PositionDao();
	private TeaminfoDao teaminfoDao = new TeaminfoDao();
	private HtmlAjax htmlAjax = new HtmlAjax();
	private NoticeTeam noticeTeam = new NoticeTeam();
	private TemplateMessage templateMessage = new TemplateMessage();
	private Log log = LogFactory.getLog(getClass());
	//通知id
	private Integer id;
	//用户id
	private Integer uid;
	//队伍id
	private Integer tid;
	//通知类型
	private String noticeType;
	//返回前台的json字符串
	private String resultStr;
	private Integer type;

	/**
	 * 
	* 描述: 查询用户所在队伍通知和全体通知<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月23日/下午3:29:55<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String findNoticeList(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
            list = noticeDao.findUserNotice(loginUser.getId(),pageNo,PAGE_SIZE);
            ActionContext.getContext().put("uid", loginUser.getId());
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 根据用户id ajax上拉加载更多通知<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-8-10/下午9:59:57<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public void ajaxLoadMoreUNoticeList() throws Exception {
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			String sql = noticeDao.getUNoticeListsql(loginUser.getId());
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, noticeDao.getTagName1(), pageNo, 0);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	/**
	 * 
	* 描述: 展示某个球队的通知列表<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月26日/下午3:54:36<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String findTeamNList(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
			list = noticeDao.findTeamNotice(tid,position.getId(),pageNo,PAGE_SIZE);
			ActionContext.getContext().put("position", position);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 上拉加载更多
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @return
	 * @throws Exception 
	 */
	public void ajaxLoadMoreNoticeList() throws Exception {
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
			String sql = noticeDao.getNoticeListSql(tid,position.getId());
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, noticeDao.getTagName(), pageNo, 0);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 
	* 描述: 跳转到添加通知页面<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月20日/上午11:45:39<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String addNotice(){
		try {
			/*WXUserSS loginUser = (WXUserSS) getUser();
			Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
			if (position == null) {
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			}
			if (position != null && position.getId() == 4) {
				addActionError("您没有权限进行此操作");
				return INPUT;
			}*/
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 展示通知详情<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月20日/下午1:52:51<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String showNoticeDetail(){
		try {
			notice = (Notice) HibernateDAO.findById(Notice.class, id);
			//通知可能会被删除，但是模板消息保留通知详情id，此时需要显示通知被删除
			if (notice.getDelState() == 1) {
				return INPUT;
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
	* 描述: 编辑通知<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月20日/下午1:52:51<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String editNotice(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
			if (position == null) {
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			}
			if (position != null && position.getId() == 4) {
				addActionError("您没有权限进行此操作");
				return INPUT;
			}
			notice = (Notice) HibernateDAO.findById(Notice.class, id);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 保存通知<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月20日/下午2:03:23<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String saveNotice(){
		try {
			boolean check = check(notice);
			if (check) {
				WXUserSS loginUser = (WXUserSS) getUser();
				UserTeam RemarkName = noticeDao.findRemarkName(loginUser.getId(), tid);
				if(notice.getId() == null){
					Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
					if (position == null) {
						return INPUT;
					}else if (position.getId() == 4) {
						return INPUT;
					}
					//System.out.println(tid);
					//if (noticeType.equals("本队成员")) {
					notice.setNoticeType(0);
					/*}else if (noticeType.equals("全体成员")){
						notice.setNoticeType(1);
					}*/
					if (upload != null || upload.get(0) != null) {
						int saveFile = saveFile(Config.getFileUploadPath());
						if(saveFile == 0){
							String path=uploadFiles.get(0).getUploadFileName();
							String path1=uploadFiles.get(0).getUploadRealName();
							notice.setAttachmentOldName(path);
							notice.setAttachmentNewName(path1);
						}
					}
					if (RemarkName != null ) {
						notice.setNickName(RemarkName.getRemakeName());
					}else {
						notice.setNickName(loginUser.getNickname());
					}
					notice.setposition(position.getId());
					notice.setCreateUser(loginUser.getId());
					notice.setCreateTime(new Date());
					notice.setDelState(0);
					notice.setType(type);
					notice.setTitle(notice.getTitle().trim());
					notice.setContent(notice.getContent().trim());
					Integer saveId = (Integer) HibernateDAO.save(notice);
					if (saveId != null) {
						// 操作日志
						String content = "添加-编号为：\"" + saveId + "\"的通知";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "通知");
					}
					//判断通知类型是否为平台通知，一期暂时不使用else代码
					if (notice.getNoticeType() == 0) {
						noticeTeam.setNid(notice.getId());
						noticeTeam.setTid(tid);
						HibernateDAO.save(noticeTeam);
					}else if (notice.getNoticeType() == 1) {
						List<Integer> list = teaminfoDao.findAllTeamId();
						for (int i = 0; i < list.size(); i++) {
							NoticeTeam noticeTeam1 = new NoticeTeam();
							noticeTeam1.setNid(notice.getId());
							noticeTeam1.setTid(list.get(i));
							HibernateDAO.save(noticeTeam1);
						}
					}
					if(type == 1){
						templateMessage.sendMes(notice.getTitle(),loginUser.getNickname(),tid,"notice/showNoticeDetail?id="+notice.getId());
					}
					HtmlAjax.getJson(getResponse(), true);
					return NONE;
				}else{
					Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
					if (position == null) {
						return INPUT;
					}else if (position.getId() == 4) {
						return INPUT;
					}
					if (upload != null || upload.get(0) != null) {
						int saveFile = saveFile(Config.getFileUploadPath());
						if(saveFile == 0){
							String path=uploadFiles.get(0).getUploadFileName();
							String path1=uploadFiles.get(0).getUploadRealName();
							notice.setAttachmentOldName(path);
							notice.setAttachmentNewName(path1);
						}
					}
					if (RemarkName != null ) {
						notice.setNickName(RemarkName.getRemakeName());
					}else {
						notice.setNickName(loginUser.getNickname());
					}
					notice.setposition(position.getId());
					notice.setModifyUser(loginUser.getId());
					notice.setModifyTime(new Date());
					notice.setDelState(0);
					notice.setType(type);
					notice.setTitle(notice.getTitle().trim());
					notice.setContent(notice.getContent().trim());
					HibernateDAO.update(notice);
					if(type == 1){
						templateMessage.sendMes(notice.getTitle(),loginUser.getNickname(),tid,"notice/showNoticeDetail?id="+notice.getId());
					}
					HtmlAjax.getJson(getResponse(), true);
					return NONE;
				}
			}else {
				HtmlAjax.getJson(getResponse(), false);
				return INPUT;
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
		
	}
	/**
	 * 
	* 描述: ajax删除通知<br/>
	*
	* @return String 返回图片原名和图片新名的json字符串
	*
	* 创建时间：2017年7月23日/下午3:30:45<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String deleteNotice(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			if (id != null) {
				Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
				if (position == null ) {
					HtmlAjax.getJson(getResponse(), false);
					return INPUT;
				}
				if (position != null && position.getId() == 4) {
					HtmlAjax.getJson(getResponse(), false);
					return INPUT;
				}
				Notice notice1 = (Notice) HibernateDAO.findById(Notice.class, id);
				notice1.setDelState(1);
				notice1.setDeleteTime(new Date());
				notice1.setDeleteUser(loginUser.getId());
				HibernateDAO.update(notice1);
				HtmlAjax.getJson(getResponse(), true);
				// 操作日志
				String content = "删除编号为：\"" + id + "\"的通知";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "删除");
			}else {
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
	* 描述: ajax发布通知<br/>
	*
	* @return String 返回图片原名和图片新名的json字符串
	*
	* 创建时间：2017年7月23日/下午3:30:45<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String sendNotice(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
			if (position == null ) {
				HtmlAjax.getJson(getResponse(), false);
			}else if (position != null && position.getId() == 4) {
				HtmlAjax.getJson(getResponse(), false);
			}else {
				noticeDao.updateNoticeType(id);
				notice = (Notice) HibernateDAO.findById(Notice.class, id);
				HtmlAjax.getJson(getResponse(), true);
				templateMessage.sendMes(notice.getTitle(),loginUser.getNickname(),tid,"notice/showNoticeDetail?id="+notice.getId());
				// 操作日志
				String content = "发布编号为：\"" + id + "\"的通知";
				AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "发布");
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
	* 描述: ajax保存文件<br/>
	*
	* @return String 返回图片原名和图片新名的json字符串
	*
	* 创建时间：2017年7月23日/下午3:30:45<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String ajaxFile(){
		try {
			
			int saveFile = saveFile(Config.getFileUploadPath());
			if (saveFile == 0) {
				JSONObject jsonObject = new JSONObject();
				ImgCompress imgCompress = new ImgCompress(upload.get(0), uploadFiles.get(0).getUploadFileName(), Config.getFileUploadPath());
				imgCompress.saveOriginalPic();
				jsonObject.put("oldName",uploadFiles.get(0).getUploadFileName());
				jsonObject.put("newName",imgCompress.getPicName());
				resultStr = jsonObject.toString();
				HtmlAjax.getJson(getResponse(), jsonObject);
			}else {
				HtmlAjax.getJson(getResponse(), null);
			}
			//ImgCompress imgCompress = new ImgCompress(upload.get(0), uploadFiles.get(0).getUploadFileName(), Config.getFileUploadPath());
			//imgCompress.resizeFix(imgCompress.getPicName(), 50, 50);
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: ajax查询队伍信息<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月23日/下午3:31:39<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String teamAjax(){
		try {
			List<Object[]> teamAjax  = noticeDao.findTeamList(uid);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray =new JSONArray();
			for (int i = 0; i < teamAjax.size(); i++) {
				jsonObject.put("title",teamAjax.get(i)[1]+"  队长:"+teamAjax.get(i)[2] );
				jsonObject.put("value",teamAjax.get(i)[0] );
				jsonArray.add(jsonObject);
			}
			JSONObject object = new JSONObject();
			object.put("array", jsonArray);
			HtmlAjax.getJson(getResponse(), object);
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 创建比赛或者训练时保存到通知表里并发布模板消息<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月23日/下午5:35:31<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String saveRaceOrTrain(Integer id,Integer teamId,String nickName,Integer type){
		try {
			//WXUserSS loginUser = (WXUserSS) getUser();
			if (type == 1) {
				Race race = (Race) HibernateDAO.findById(Race.class, id);
				/*notice.setTitle(race.getRaceName());
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String date1 = format.format(race.getStartTime());
				String date2 = format.format(race.getEndTime());
				notice.setContent("\n"+"比赛开赛时间："+date1+"<br />比赛结束时间："
						+date2+"<br />比赛地点："+race.getPlace()+"<br />比赛对手："
						+race.getOpponent());
				notice.setNoticeType(0);
				notice.setNickName(nickName);
				notice.setCreateTime(new Date());
				notice.setCreateUser(loginUser.getId());
				notice.setType(1);
				HibernateDAO.save(notice);
				noticeTeam.setNid(notice.getId());
				noticeTeam.setTid(teamId);
				Integer saveId = (Integer) HibernateDAO.save(noticeTeam);
				if (saveId != null) {
					// 操作日志
					String content = "添加-编号为：\"" + saveId + "\"的通知";
					AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "添加");
				}*/
				//templateMessage.sendMes(race.getRaceName(),nickName,teamId,"race/showMesDetails?id ="+id);
				templateMessage.RaceMessage(race, nickName, teamId, "race/showMesDetails?id ="+id);
			}else if (type == 2) {
				Train train = (Train) HibernateDAO.findById(Train.class, id);
				/*notice.setTitle(train.getTrainName());
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String date1 = format.format(train.getStartTime());
				String date2 = format.format(train.getEndTime());
				notice.setContent("开始时间："+date1+"<br />结束时间："+
						date2+"<br />训练地点："+train.getTrainPlace()+
						"<br />训练内容："+train.getTrainContent());
				notice.setNoticeType(0);
				notice.setNickName(nickName);
				notice.setCreateTime(new Date());
				notice.setCreateUser(loginUser.getId());
				notice.setType(1);
				Integer saveId = (Integer) HibernateDAO.save(notice);
				if (saveId != null) {
					// 操作日志
					String content = "添加-编号为：\"" + saveId + "\"的通知";
					AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "添加");
				}
				noticeTeam.setNid(notice.getId());
				noticeTeam.setTid(teamId);
				HibernateDAO.save(noticeTeam);*/
				//templateMessage.sendMes(train.getTrainName(),nickName,teamId,"train/showMesDetails?id="+id);
				templateMessage.TrainMessage(train, nickName, teamId, "train/showMesDetails?id="+id);
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
	* 描述: 后台校验数据方法<br/>
	*
	* @return boolean 返回类型
	*
	* 创建时间：2017年7月25日/下午3:38:06<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public boolean check(Notice notice){
		boolean flag = true;
		if (notice.getTitle().equals("")||notice.getTitle().length()>20) {
			flag = false;
		}
		if (notice.getContent().length() == 0 || notice.getContent().length()>200) {
			flag = false;
		}
		return flag;
	}
	
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Notice getNotice() {
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getResultStr() {
		return resultStr;
	}
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public NoticeTeam getNoticeTeam() {
		return noticeTeam;
	}
	public void setNoticeTeam(NoticeTeam noticeTeam) {
		this.noticeTeam = noticeTeam;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
