package com.ningxun.wxuserinfo.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.file.ImgCompress;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.team.dao.TeaminfoDao;
import com.ningxun.tools.Config;
import com.ningxun.tools.TokenThread;
import com.ningxun.wxuserinfo.dao.LocationDAO;
import com.ningxun.wxuserinfo.dao.WXUserDAO;
import com.ningxun.wxuserinfo.vo.Location;
import com.ningxun.wxuserinfo.vo.UserLocation;
import com.ningxun.wxuserinfo.vo.WXUser;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class WXUserAction extends BaseSupportAction {

	// 用于存储session中openID的静态常量
	public static final String OPENID = "openid";
	// 用于记录日志
	private Log log = LogFactory.getLog(getClass());
	// 用于查询
	private WXUserDAO wxUserDAO = new WXUserDAO();
	// 微信用户
	private WXUser wxuser = new WXUser();
	// 常踢位置DAO
	private LocationDAO locationDAO = new LocationDAO();
	// 球队dao
	private TeaminfoDao teaminfoDao = new TeaminfoDao();
	// 用户id
	private Integer id;
	// 性别
	private String sex;
	// 常踢位置
	private String location;
	// 左右脚
	private String habitfoot;
	// 生日
	private String birthday;
	// 微信返回的code码
	private String code;
	// 点赞数
	private String likeNum;
	// 微信用户openID
	private String openId;
	//创建球队列表
	private List<Object> list;
	/**
	 * 
	 * 描述: 用户授权登录公众号<br/>
	 * 
	 * @return String 返回类型
	 * 
	 *         创建时间：2017-7-20/上午9:52:18<br/>
	 * @author Administrator
	 * @version V1.0 
	 * 访问地址：(更改公众号、服务器和域名的时候，只需将appid和redirect_uri修改即可)
	 *          https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx284d81335a0084c8
	 *          &redirect_uri=http%3A%2F%2Fsggk8r.natappfree.cc%2Frongqiu%2FgetToken.action
	 *          &response_type=code&scope=snsapi_userinfo&state=1
	 *          &connect_redirect=1#wechat_redirect
	 */
	public String getToken() {
		try {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
					+ "appid=" + Config.getAppid() + "&secret=" + Config.getAppsecret()
					+ "&code=" + code + "&grant_type=authorization_code";
			// 这里请求获取微信用户接口
			JSONObject jsonObject = doGetJson(url);
			String openid = jsonObject.getString("openid");
			String token = jsonObject.getString("access_token");
			
			String infoUrl = "https://api.weixin.qq.com/sns/userinfo?"
					+ "access_token=" + token + "&openid=" + openid
					+ "&lang=zh_CN";
			// 获取微信用户信息
			JSONObject accessUser = doGetJson(infoUrl);
			
			//判断用户是否关注微信公众号
			boolean judgeIsFollow = Config.judgeIsFollow(TokenThread.accessToken.getToken(), openid);
			if (!judgeIsFollow) {
				//未关注
				return "toFollow";
			}
			
			WXUser wxUser = new WXUser();
			wxUser.setOpenId(openid);
			// 判断用户是否是第一次登录系统
			WXUser isNewUser = wxUserDAO.findWxUserByOpenId(openid);
			
			if (isNewUser == null) {
				// 如果是第一次登录，则保存微信的用户信息，然后跳转注册页面
				wxUser.setNickname(accessUser.getString("nickname"));
				if (accessUser.getString("headimgurl").equals("/0")) {
					wxUser.setHeadPortraitNew(null);
				} else {
					wxUser.setHeadPortraitNew(accessUser.getString("headimgurl"));
				}
				wxUser.setSex(Integer.valueOf(accessUser.getString("sex")));
				if (accessUser.getString("sex").equals("0")) {
					// 如果从微信中获取到的用户性别为0，默认设置为男
					wxUser.setSex(1);
				}
				// 获取用户的省市
				wxUser.setProvince(accessUser.getString("province"));
				wxUser.setCity(accessUser.getString("city"));
				wxUser.setCreateTime(new Date());
				// 设置该用户未绑定
				wxUser.setIsBind(0);
				// 设置该用户的点赞数为0
				wxUser.setLikeNum(0);
				// 设置用户未删除
				wxUser.setDelState(0);
				// 设置用户未过期
				wxUser.setAccountNonExpired(0);
				// 设置用户密码未过期
				wxUser.setCredentialsNonExpired(0);
				// 设置账户未锁定
				wxUser.setAccountNonLocked(0);
				// 设置当前用户是否可用
				wxUser.setEnabled(0);
				// 设置当前用户为注册用户
				wxUser.setUserType(1);
				ActionContext.getContext().getValueStack().set("wxuser", wxUser);
				HibernateDAO.save(wxUser);
				// 获取常踢位置的列表
				List<Location> locations = locationDAO.getList();
				if (locations != null) {
					JSONArray jsonArray = new JSONArray();
					for (int i = 0; i < locations.size(); i++) {
						JSONObject jsonObject2 = new JSONObject();
						jsonObject2.put("title", locations.get(i)
								.getLocationName());
						jsonObject2.put("value", locations.get(i).getId());
						jsonArray.add(jsonObject2);
					}
					ActionContext.getContext().getValueStack()
							.set("locations", jsonArray.toString());
				}
				return "toregist";
			} else {
				// 将用户信息存入值栈中
				ActionContext.getContext().getValueStack().set("wxuser", isNewUser);
				// 如果不是第一次登录
				if (isNewUser.getIsBind() == 0) {
					// 如果未绑定，则跳转注册页
					// 查询出常踢位置的列表并存入值栈，传递到注册页面上
					List<Location> locations = locationDAO.getList();
					if (locations != null) {
						JSONArray jsonArray = new JSONArray();
						for (int i = 0; i < locations.size(); i++) {
							JSONObject jsonObject2 = new JSONObject();
							jsonObject2.put("title", locations.get(i)
									.getLocationName());
							jsonObject2.put("value", locations.get(i).getId());
							jsonArray.add(jsonObject2);
						}
						ActionContext.getContext().getValueStack()
								.set("locations", jsonArray.toString());
					}
					return "toregist";
				} else {
					// 如果已注册，进入安全框架，存放微信用户信息，之后跳转主页
					openId = openid;
					return "nxback";
				}
			}
		} catch (Exception e) {
			return ERROR;
		}
	}
	/**
	 * 
	 * 描述: 跳转用户编辑页面<br/>
	 * 
	 * @return String 返回类型
	 * 
	 *         创建时间：2017-7-21/上午7:25:59<br/>
	 * @author Administrator
	 * @version V1.0
	 */
	public String userAdd() {
		try {
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			wxuser = (WXUser) HibernateDAO.findById(WXUser.class, wxUserSS.getId());
			ActionContext.getContext().getValueStack().set("wxuser", wxuser);
			// 查询用户常踢位置
			List<Location> locations = locationDAO.findUserLocation(wxuser.getId());
			String locationString = "";
			if (locations != null && locations.size() > 0) {
				for (int i = 0; i < locations.size(); i++) {
					
					if (i == locations.size() - 1) {
						locationString += locations.get(i).getLocationName();
					} else {
						locationString += locations.get(i).getLocationName()+ ",";
					}
				}
				ActionContext.getContext().getValueStack()
						.set("myLocation", locationString);
			}
			
			// 获取常踢位置的列表
			List<Location> locationTotal = locationDAO.getList();
			if (locationTotal != null) {
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < locationTotal.size(); i++) {
					JSONObject jsonObject2 = new JSONObject();
					jsonObject2.put("title", locationTotal.get(i)
							.getLocationName());
					jsonObject2.put("value", locationTotal.get(i).getId());
					jsonArray.add(jsonObject2);
				}
				
				ActionContext.getContext().getValueStack()
						.set("locationTotal", jsonArray.toString());
			}
			
			
			/*ActionContext.getContext().getValueStack()
					.set("locationString", locationString);*/
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	 * 描述: 查看用户详细信息<br/>
	 * 
	 * @return String 返回类型
	 * 
	 *         创建时间：2017-7-21/下午12:22:26<br/>
	 * @author Administrator
	 * @version V1.0
	 */
	public String userDetail() {
		try {
			
			// 获取当前登录用户
			WXUserSS wxUserSS = (WXUserSS) getUser();
			wxuser = (WXUser) HibernateDAO.findById(WXUser.class, wxUserSS.getId());
			
			// 加载球队列表
			//我创建的球队
			List<Object[]> createTeams = teaminfoDao.getCreateTeamByUid(wxuser.getId());
			//我管理的球队
			List<Object[]> manageTeams = teaminfoDao.getManageTeamByUid(wxuser.getId());
			//我加入的球队
			List<Object[]> joinTeams = teaminfoDao.getJoinTeamByUid(wxuser.getId());
			ActionContext.getContext().put("createTeams", createTeams);
			ActionContext.getContext().put("manageTeams", manageTeams);
			ActionContext.getContext().put("joinTeams", joinTeams);
			
			// 将用户放入值栈
			ActionContext.getContext().getValueStack().set("wxuser", wxuser);
			// 查询用户常踢位置
			List<Location> locations = locationDAO.findUserLocation(wxuser
					.getId());
			String locationString = "";
			if (locations != null && locations.size() > 0) {
				for (int i = 0; i < locations.size(); i++) {
					if (i == locations.size() - 1) {
						locationString += locations.get(i).getLocationName();
					} else {
						locationString += locations.get(i).getLocationName()+ ",";
					}
				}
			}
			ActionContext.getContext().getValueStack()
					.set("locationString", locationString);
			//查看当前用户是否创建过球队
			list = wxUserDAO.findTeamCreatorList(wxUserSS.getId());
			ActionContext.getContext().getValueStack().set("list", list);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	 * 描述: 用户注册和用户编辑<br/>
	 * 
	 * @return String 返回类型
	 * 
	 *         创建时间：2017-7-20/下午8:00:51<br/>
	 * @author Administrator
	 * @version V1.0
	 * @throws Exception
	 */
	public String userSave() {
		try {
			if (wxuser.getId() == null) {
				// 用户注册
				// 添加
				WXUser wxUser = wxUserDAO.findWxUserByOpenId(wxuser.getOpenId());
				wxUser.setNickname(wxuser.getNickname());
				wxUser.setPhone(wxuser.getPhone());
				wxUser.setCreateTime(new Date());
				wxUser.setCreateUser(wxUser.getId());
				// 前台传过来的性别、左右脚、常踢位置进行处理
				if (sex.trim().equals("男")) {
					wxUser.setSex(1);
				} else {
					wxUser.setSex(2);
				}
				if (habitfoot.trim().equals("左脚")) {
					wxUser.setHabitfoot(1);
				} else {
					wxUser.setHabitfoot(2);
				}
				// 处理常踢位置的字符串
				// System.out.println("location == " + location);
				if (location.trim() != null && !location.trim().equals("")) {
					String[] locations = location.split(",");
					for (int i = 0; i < locations.length; i++) {
						Integer id = locationDAO.findLocationByName(
								locations[i]).getId();
						UserLocation userLocation = new UserLocation();
						userLocation.setLid(id);
						userLocation.setUid(wxUser.getId());
						HibernateDAO.save(userLocation);
					}
				}
				// 这里微信用户已与系统绑定
				wxUser.setIsBind(1);
				wxUser.setDelState(0);
				// 设置用户未过期
				wxUser.setAccountNonExpired(0);
				// 设置用户密码未过期
				wxUser.setCredentialsNonExpired(0);
				// 设置账户未锁定
				wxUser.setAccountNonLocked(0);
				// 设置当前用户是否可用
				wxUser.setEnabled(0);
				// 设置当前用户为注册用户
				wxUser.setUserType(1);
				//记录日志
				String content = "新增-编号为：\"" + wxUser.getId() + "\"的用户信息";
				AddLog.addOperateLog(String.valueOf(wxUser.getId()), wxUser.getNickname(),  "", content, "添加");
				//保存，这里依旧是更新
				HibernateDAO.update(wxUser);
				// 传递给安全登录框架
				openId = wxuser.getOpenId();
				return "nxback";
			} else {
				// 用户修改信息
				// 编辑
				// 前台传过来的性别、左右脚以及生日字符串进行处理
				if (sex.trim().equals("男")) {
					wxuser.setSex(1);
				} else if(sex.trim().equals("女")) {
					wxuser.setSex(2);
				} else {
					wxuser.setSex(0);
				}
				if (habitfoot.trim().equals("左脚")) {
					wxuser.setHabitfoot(1);
				} else {
					wxuser.setHabitfoot(2);
				}
				if (birthday.trim() != null && !birthday.trim().equals("")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = sdf.parse(birthday);
					wxuser.setBirthday(date);
				}
				// 对前台传过来的常踢位置信息进行保存
				if (location.trim() != null && !location.trim().equals("")) {
					// 先将对应关系删除，然后再进行添加
					List<UserLocation> userLocations = locationDAO.findUserLocationByUid(wxuser.getId());
					for (int i = 0; i < userLocations.size(); i++) {
						HibernateDAO.delete(userLocations.get(i));
					}
					String[] locations = location.split(",");
					for (int i = 0; i < locations.length; i++) {
						Integer id = locationDAO.findLocationByName(
								locations[i]).getId();
						UserLocation userLocation = new UserLocation();
						userLocation.setLid(id);
						userLocation.setUid(wxuser.getId());
						HibernateDAO.save(userLocation);
					}
				}
				// 保存头像
				int saveFile = saveFile(Config.getFileUploadPath());
				// 0:成功,1:上传文件为空,2:异常
				if (upload != null) {
					if (saveFile == 0) {
						/*String path = uploadFiles.get(0).getUploadFileName();
						String path1 = uploadFiles.get(0).getUploadRealName();*/
						ImgCompress imgCompress = new ImgCompress(upload.get(0), uploadFiles.get(0).getUploadFileName(), Config.getFileUploadPath());
						// imgCompress.resizeFix(imgCompress.getPicName(), 50, 50);
						
						wxuser.setHeadPortrait(uploadFiles.get(0).getUploadFileName());
						wxuser.setHeadPortraitNew("max_" + imgCompress.getPicName());
					}
				}
				
				wxuser.setModifyTime(new Date());
				wxuser.setModifyUser(wxuser.getId());
				wxuser.setDelState(0);
				// 设置用户未过期
				wxuser.setAccountNonExpired(0);
				// 设置用户密码未过期
				wxuser.setCredentialsNonExpired(0);
				// 设置账户未锁定
				wxuser.setAccountNonLocked(0);
				// 设置当前用户是否可用
				wxuser.setEnabled(0);
				// 设置当前用户为注册用户
				wxuser.setUserType(1);
				String content = "更改-编号为：\"" + wxuser.getId() + "\"的用户信息";
				AddLog.addOperateLog(String.valueOf(wxuser.getId()), wxuser.getNickname(),  "", content, "添加");
				//编辑
				HibernateDAO.update(wxuser);
				// 将id压入值栈，传给跳转页面
				// 获取当前登录用户
				/*WXUserSS wxUserSS = (WXUserSS) getUser();
				id = wxUserSS.getId();*/
				return "my";
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}

	/**
	 * 
	 * 描述: 异步请求点赞<br/>
	 * 
	 * @return String 返回类型
	 * 
	 *         创建时间：2017-7-22/上午8:18:08<br/>
	 * @author Administrator
	 * @version V1.0
	 */
	public String dianzan() {
		try {
			if (likeNum.trim() != null && !likeNum.trim().equals("")) {
				// 从前台传来的字符串提取数字
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(likeNum);
				String temp = m.replaceAll("").trim();

				if (id != null) {

					wxuser = (WXUser) HibernateDAO.findById(WXUser.class, id);
					wxuser.setLikeNum(wxuser.getLikeNum() + 1);
					HibernateDAO.update(wxuser);
					// 点赞数加一，返回页面
					Integer currentLikeNum = Integer.parseInt(temp) + 1;
					likeNum = currentLikeNum + "";
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
	}
	/**
	 * 
	 * 描述: 通过url获取json对象<br/>
	 * 
	 * @return JSONObject 返回类型
	 * 
	 *         创建时间：2017-7-23/上午7:58:37<br/>
	 * @author Administrator
	 * @version V1.0
	 */
	public static JSONObject doGetJson(String url) throws Exception {
		JSONObject jsonObject = null;
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 通过get方式向微信服务器发送请求
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		// 释放链接
		httpGet.releaseConnection();
		return jsonObject;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHabitfoot() {
		return habitfoot;
	}

	public void setHabitfoot(String habitfoot) {
		this.habitfoot = habitfoot;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(String likeNum) {
		this.likeNum = likeNum;
	}
	public WXUser getWxuser() {
		return wxuser;
	}
	public void setWxuser(WXUser wxuser) {
		this.wxuser = wxuser;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
