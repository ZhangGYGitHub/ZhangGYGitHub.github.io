package com.ningxun.base.user.action;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.base.user.dao.UserInfoDAO;
import com.ningxun.base.user.vo.Userinfo;
import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.common.SysConfig;
import com.ningxun.mail.SendMail;
import com.ningxun.security.action.UserInfoSS;
import com.ningxun.util.EncryptMD5;
import com.ningxun.util.UniquenessDetect;
import com.ningxun.util.ValidateUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * <li>技术支持:河北凝讯科技有限公司<br/> <li>项目名称: jjpt<br/> <li>文件名: StudentUserAction.java<br/> <li>创建时间: 2016-9-10 下午9:35:03<br/>
 * 
 * @author huayu
 * @version [v1.00]
 */
@SuppressWarnings("serial")
public class UserAction extends BaseSupportAction {

	private List<Userinfo> userList;// 用户列表
	private Userinfo userinfo;// 用户对象
	private Integer id;
	private String zoneId;// 区域ID
	private boolean saveOk = false;// 保存成功标志
	private Integer type;// 登录用户类型
//	private List<Organize> firstLevelList;// 一级单位列表
//	private List<Organize> secondLevelList;// 二级单位列表
//	private List<Organize> thirdLevelList;// 三级单位列表
//	private List<Organize> fourthLevelList;// 四级单位列表

	private boolean accountNonExpired = true;// 账号未过期
	private boolean credentialsNonExpired = true;// 密码未过期
	private boolean accountNonLocked = true;// 账号未锁定

	private Log log = LogFactory.getLog(getClass());
	private String logContent = "";// 操作日志内容
	private String operation = "";// 操作日志-操作

	private String userNameCX;// 用户名查询
	private String emailCX;// 电子邮箱
	private String realNameCX;// 真实姓名
	private String firstIdCX;// 一级
	private String secondIdCX;// 二级
	private String thirdIdCX;// 三级
	private String fourthIdCX;// 四级
	private String companyNameCX;// 组织名查询
	private String levelIdCX;// 级别Id

	private String passwordOld;// 原密码
	private String passwordNew;// 新密码
	private String passwordConfirm;// 确认密码
	private String userName;// 找回密码的帐号
	private String email;// 注册的电子邮箱
	private String userIdentity;// 用户串码

	private List<Object> roleList;// 角色列表
	private String roleNameCX;// 角色名
	private String allPageCKB;// 本页所有数据授权复选按钮
	private String selectCKB;// 本页被选中的数据授权复选按钮
//	private List<Userinforole> userInfoRoleList;// 用户角色对应关系列表
//	private RoleDAO roleDAO = new RoleDAO();
	private UserInfoDAO userInfoDAO = new UserInfoDAO();

	/**
	 * 描述: 按条件查询用户列表<br/>
	 * 
	 * @return
	 * @return String 返回类型 创建时间：2016-9-10/下午8:48:53<br/>
	 * @author huayu
	 */
	public String showUserList() {
		try {

			UserInfoSS userInfoSS = (UserInfoSS) getUser();
			type = userInfoSS.getUserType();// 0：自注册用户；1：集体注册用户；2：平台管理员；3：区域管理员
			// 初始化辖区列表数据
//			try {
//				firstLevelList = studentUserDAO.findOrganizesByPid("1");
//			} catch (Exception e) {
//				firstLevelList = new ArrayList<Organize>();
//			}
//			try {
//				secondLevelList = studentUserDAO.findOrganizesByPid(firstIdCX);
//			} catch (Exception e) {
//				secondLevelList = new ArrayList<Organize>();
//			}
//			try {
//				thirdLevelList = studentUserDAO.findOrganizesByPid(secondIdCX);
//			} catch (Exception e) {
//				thirdLevelList = new ArrayList<Organize>();
//			}
//			try {
//				fourthLevelList = studentUserDAO.findOrganizesByPid(thirdIdCX);
//			} catch (Exception e) {
//				fourthLevelList = new ArrayList<Organize>();
//			}
			if (type == 2) {// 平台管理员

				// 设置辖区
				if (null == firstIdCX || firstIdCX.equals("")) {
					levelIdCX = "";
				} else {
					if (null == secondIdCX || secondIdCX.equals("")) {
						levelIdCX = firstIdCX;
					} else if (null == thirdIdCX || thirdIdCX.equals("")) {
						levelIdCX = secondIdCX;
					} else if (null == fourthIdCX || fourthIdCX.equals("")) {
						levelIdCX = thirdIdCX;
					} else {
						levelIdCX = fourthIdCX;
					}
				}

				userList = userInfoDAO.findUserList(userInfoSS, userNameCX, realNameCX, levelIdCX, pageNo, PAGE_SIZE);
			} else {
				return "auth";
			}
			resultRows = userInfoDAO.getResultRows();
			pageCount = userInfoDAO.getPageCount();
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			return ERROR;
		}
	}

	/**
	 * 描述: 调用添加或编辑管理员用户页面<br/>
	 * 
	 * @return String 返回类型 创建时间：2016-9-11/上午8:26:52<br/>
	 * @author huayu
	 */
	public String showManageAdd() {
		try {
			// 初始化列表数据
//			try {
//				firstLevelList = studentUserDAO.findOrganizesByPid("1");
//			} catch (Exception e) {
//				firstLevelList = new ArrayList<Organize>();
//			}
//			try {
//				secondLevelList = studentUserDAO.findOrganizesByPid(((Organize) firstLevelList.get(0)).getId().toString());
//			} catch (Exception e) {
//				secondLevelList = new ArrayList<Organize>();
//			}
//			thirdLevelList = new ArrayList<Organize>();
//			fourthLevelList = new ArrayList<Organize>();

			if (id != null) {// 编辑用户
				userinfo = (Userinfo) HibernateDAO.findById(Userinfo.class, id);
				if (userinfo.getAccountNonExpired() != null) {
					accountNonExpired = userinfo.getAccountNonExpired() == 0 ? true : false;
				}
				if (userinfo.getAccountNonLocked() != null) {
					accountNonLocked = userinfo.getAccountNonLocked() == 0 ? true : false;
				}
				if (userinfo.getCredentialsNonExpired() != null) {
					credentialsNonExpired = userinfo.getCredentialsNonExpired() == 0 ? true : false;
				}

				// 单位部门列表
//				try {
//					firstLevelList = studentUserDAO.findOrganizesByPid("1");
//				} catch (Exception e) {
//					firstLevelList = new ArrayList<Organize>();
//				}
//				try {
//					secondLevelList = studentUserDAO.findOrganizesByPid(((Organize) firstLevelList.get(0)).getId().toString());
//				} catch (Exception e) {
//					secondLevelList = new ArrayList<Organize>();
//				}
//				try {
//					thirdLevelList = studentUserDAO.findOrganizesByPid(userinfo.getCompanyLevel2());
//				} catch (Exception e) {
//					thirdLevelList = new ArrayList<Organize>();
//				}
//				try {
//					fourthLevelList = studentUserDAO.findOrganizesByPid(userinfo.getCompanyLevel3());
//				} catch (Exception e) {
//					fourthLevelList = new ArrayList<Organize>();
//				}
			}
			return SUCCESS;

		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
	}

	/**
	 * 描述: 保存管理员用户信息<br/>
	 * 
	 * @return
	 * @return String 返回类型 创建时间：2016-9-11/上午9:51:05<br/>
	 * @author huayu
	 */
	public String saveManageUser() {
		try {
			UserInfoSS userInfos = (UserInfoSS) getUser();
			userinfo.setAccountNonExpired(accountNonExpired ? 0 : 1);
			userinfo.setCredentialsNonExpired(credentialsNonExpired ? 0 : 1);
			userinfo.setAccountNonLocked(accountNonLocked ? 0 : 1);

			userinfo.setUserName(userinfo.getUserName().trim());
			userinfo.setRealName(userinfo.getRealName().trim());
			userinfo.setMobilNo(userinfo.getMobilNo().trim());
			userinfo.setEmail(userinfo.getEmail().trim());
			userinfo.setPhone(userinfo.getPhone().trim());
			userinfo.setAddress(userinfo.getAddress().trim());

			// 设置辖区
			if (null == userinfo.getCompanyLevel2() || userinfo.getCompanyLevel2().equals("")) {
				userinfo.setCompanyLevel(userinfo.getCompanyLevel1());
			} else if (null == userinfo.getCompanyLevel3() || userinfo.getCompanyLevel3().equals("")) {
				userinfo.setCompanyLevel(userinfo.getCompanyLevel2());
			} else if (null == userinfo.getCompanyLevel4() || userinfo.getCompanyLevel4().equals("")) {
				userinfo.setCompanyLevel(userinfo.getCompanyLevel3());
			} else {
				userinfo.setCompanyLevel(userinfo.getCompanyLevel4().toString());
			}

			if (userInfos.getUserType() == 2) {// 平台管理员
				if (userinfo.getId() == null) {// 添加
					userinfo.setEnabled(0);
					userinfo.setCreateTime(new Date());
					userinfo.setCreateUser(userInfos.getId());
					// 所要添加的管理员类型
					userinfo.setUserType(3);// 添加区域管理员

					try {
						userinfo.setPassword(EncryptMD5.MD5(SysConfig.getConfigData("Password", "value", "初始密码读取错误")));
					} catch (Exception e) {
						userinfo.setPassword(EncryptMD5.MD5("123456"));
					}
					if (null != HibernateDAO.merge(userinfo)) {
						// 操作日志
						operation = "添加";
						logContent = "添加用户——用户名为：" + userinfo.getUserName() + "的用户信息";
						AddLog.addOperateLog(userInfos.getUserName(), userInfos.getRealName(), userInfos.getCompanyLevel() + "", logContent, operation);
					} else {
						return ERROR;
					}

				} else {// 编辑

					Userinfo tUserInfo = (Userinfo) HibernateDAO.findById(Userinfo.class, userinfo.getId());// 取回密码
					userinfo.setModifyTime(new Date());
					userinfo.setModifyUser(userInfos.getId());
					userinfo.setCreateTime(tUserInfo.getCreateTime());
					userinfo.setCreateUser(tUserInfo.getCreateUser());
					userinfo.setUserName(tUserInfo.getUserName());
					userinfo.setPassword(tUserInfo.getPassword());
					userinfo.setUserType(tUserInfo.getUserType());
					userinfo.setEnabled(tUserInfo.getEnabled());

					if (null != HibernateDAO.merge(userinfo)) {
						// 操作日志
						operation = "编辑";
						logContent = "编辑用户——用户名为：" + userinfo.getUserName() + "的用户信息";
						AddLog.addOperateLog(userInfos.getUserName(), userInfos.getRealName(), userInfos.getCompanyLevel() + "", logContent, operation);
					} else {
						return ERROR;
					}
				}
			}

			return SUCCESS;
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
	}

	/**
	 * 描述: 服务器端用户信息校验<br/>
	 * 
	 * @return void 返回类型 创建时间：2016-9-11/上午9:17:09<br/>
	 * @author huayu
	 */
	public void validateSaveManageUser() {

		UniquenessDetect uniquenessDetect = new UniquenessDetect();
		Boolean haveValidate = true;

		Object checkProperty = userinfo.getUserName();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "userName", "用户名", 28, true)) {
			haveValidate = false;
		}
		if (userinfo.getId() == null) {// 添加
			if (!uniquenessDetect.detectBySql("userinfo", "userName", checkProperty)) {
				haveValidate = false;
				this.addFieldError("userName", "用户名已存在!");
			}
		} else {
			if (!uniquenessDetect.detectExcludedIDBySql("userinfo", "userName", checkProperty, userinfo.getId())) {
				haveValidate = false;
				this.addFieldError("userName", "用户名已存在!");
			}
		}

		checkProperty = userinfo.getRealName();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "realName", "真实姓名", 22, true)) {
			haveValidate = false;
		}

		checkProperty = userinfo.getEmail();
		if (!ValidateUtil.submitCheck(this, "邮箱", checkProperty, "email", "电子邮箱", 80, true)) {
			haveValidate = false;
		}
		if (userinfo.getId() == null) {
			if (!uniquenessDetect.detectBySql("userinfo", "email", checkProperty)) {
				haveValidate = false;
				this.addFieldError("email", "邮箱已被使用!");
			}
		} else {
			if (!uniquenessDetect.detectExcludedIDBySql("userinfo", "email", checkProperty, userinfo.getId())) {
				haveValidate = false;
				this.addFieldError("email", "邮箱已被使用!");
			}
		}

		checkProperty = userinfo.getMobilNo();
		if (!ValidateUtil.submitCheck(this, "手机", checkProperty, "mobilNo", "手机号码", 11, true)) {
			haveValidate = false;
		}

		checkProperty = userinfo.getPhone();
		if (!ValidateUtil.submitCheck(this, "固话", checkProperty, "phone", "固定电话", 28, false)) {
			haveValidate = false;
		}

		checkProperty = userinfo.getAddress();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "address", "联系地址", 48, false)) {
			haveValidate = false;
		}

		if (haveValidate) {
			return;
		} else {// 校验不通过初始化辖区列表
			// 管辖区域列表
//			try {
//				firstLevelList = studentUserDAO.findOrganizesByPid("1");
//			} catch (Exception e) {
//				firstLevelList = new ArrayList<Organize>();
//			}
//			try {
//				secondLevelList = studentUserDAO.findOrganizesByPid(((Organize) firstLevelList.get(0)).getId().toString());
//			} catch (Exception e) {
//				secondLevelList = new ArrayList<Organize>();
//			}
//			try {
//				thirdLevelList = studentUserDAO.findOrganizesByPid(userinfo.getCompanyLevel2().toString());
//			} catch (Exception e) {
//				thirdLevelList = new ArrayList<Organize>();
//			}
//			try {
//				fourthLevelList = studentUserDAO.findOrganizesByPid(userinfo.getCompanyLevel3().toString());
//			} catch (Exception e) {
//				fourthLevelList = new ArrayList<Organize>();
//			}
		}
	}

	/**
	 * 描述: 删除管理员用户信息<br/>
	 * 
	 * @return String 返回类型 创建时间：2016-9-11/上午10:11:29<br/>
	 * @author huayu
	 */
	public String deleteManageUser() {
		try {
			UserInfoSS userInfos = (UserInfoSS) getUser();
			userinfo = (Userinfo) HibernateDAO.findById(Userinfo.class, id);

			if (userinfo.getUserType() == 2) {// 平台管理员不允许删除
				addActionMessage("此账号不允许删除，请点击[返回]后刷新页面，如不能解决请联系管理员!");
				return ERROR;
			}

			userinfo.setEnabled(1);
			userinfo.setDeleteTime(new Date());
			userinfo.setDeleteUser(userInfos.getId());
			if (null != HibernateDAO.merge(userinfo)) {
				String delString = "delete from userinforole where userInfoId = '" + userinfo.getId() + "'";
				HibernateDAO.batchBySql(delString);// 停用账号后删除其用户-角色对应关系

				// 操作日志
				operation = "删除";
				logContent = "删除用户——用户名为：" + userinfo.getUserName() + "\"的用户信息";
				AddLog.addOperateLog(userInfos.getUserName(), userInfos.getRealName(), userInfos.getCompanyLevel() + "", logContent, operation);
			} else {
				addActionMessage("删除用户信息出错，请点击[返回]后刷新页面，如不能解决请联系管理员!");
				return ERROR;
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);
			addActionMessage("删除用户信息出错，请点击[返回]后刷新页面，如不能解决请联系管理员!");
			return ERROR;
		}
	}

	/**
	 * <li>功能描述：重置用户密码。
	 * 
	 * @return String
	 */
	public String resetPassword() {
		try {
			if (id != null) {
				UserInfoSS userInfos = (UserInfoSS) getUser();
				userinfo = (Userinfo) HibernateDAO.findById(Userinfo.class, id);
				try {
					userinfo.setPassword(EncryptMD5.MD5(SysConfig.getConfigData("Password", "value", "初始密码读取错误")));
				} catch (Exception e) {
					userinfo.setPassword(EncryptMD5.MD5("123456"));
				}
				if (HibernateDAO.merge(userinfo) == null) {
					addActionMessage("重置用户密码出错，请点击[返回]后刷新页面，如不能解决请联系管理员!");
					return ERROR;
				} else {
					// 操作日志
					operation = "重置密码";
					logContent = "用户重置密码——用户名为：" + userinfo.getUserName() + "\"的用户信息";
					AddLog.addOperateLog(userInfos.getUserName(), userInfos.getRealName(), userInfos.getCompanyLevel() + "", logContent, operation);
					return SUCCESS;
				}
			}
			return SUCCESS;

		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("重置用户密码出错，请点击[返回]后刷新页面，如不能解决请联系管理员!");
			return ERROR;
		}
	}

	/**
	 * <li>功能描述：分配公司管理员角色。
	 * 
	 * @return String
	 */
	public String allotRoles() {
		try {
			if (id != null) {// 编辑页面
				UserInfoSS userInfos = (UserInfoSS) getUser();
//				roleList = roleDAO.findAll(pageNo, PAGE_SIZE, roleNameCX, id, "1", userInfos.getUserType());
//				resultRows = roleDAO.getResultRows();
//				pageCount = roleDAO.getPageCount();
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			return ERROR;
		}
	}

	/**
	 * <li>功能描述：保存用户角色。
	 * 
	 * @return String
	 */
	public String saveAllotRoles() {
		try {
			if (id != null) {// 编辑页面
				String[] tempAllStrings = allPageCKB.split(",");
				String[] tempSelectStrings = selectCKB.split(",");

				if (selectCKB != null && allPageCKB != null && !allPageCKB.equals("")) {
					for (int i = 0; i < tempAllStrings.length; i++) {// 删除当前页的用户角色关系
						String delString = "delete from userinforole where userInfoId = '" + id + "' and roleId = " + tempAllStrings[i];
						HibernateDAO.batchBySql(delString);
					}
					for (int i = 0; i < tempSelectStrings.length; i++) {// 添加当前页的用户角色关系
						String saveString = "INSERT INTO userinforole (userInfoId, roleId) VALUES  (" + id + "," + tempSelectStrings[i] + ")";
						HibernateDAO.batchBySql(saveString);
					}

				}
			}

			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			return ERROR;
		}

	}

	/**
	 * 描述: 查看用户详情<br/>
	 * 
	 * @return String 返回类型 创建时间：2016-9-13/下午7:06:47<br/>
	 * @author huayu
	 */
	public String seeUserDetails() {
		try {
			if (id != null) {// 查看用户
				userinfo = userInfoDAO.findByUserId(id);
			}
			return SUCCESS;

		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
	}

	/**
	 * 描述: 用户修改个人信息<br/>
	 * 
	 * @return
	 * @return String 返回类型 创建时间：2016-9-11/下午4:05:52<br/>
	 * @author huayu
	 */
	public String userInformationEdit() {
		try {
			UserInfoSS userInfos = (UserInfoSS) getUser();
			if (userInfos.getId() == id) {
				userinfo = (Userinfo) HibernateDAO.findById(Userinfo.class, userInfos.getId());
				return SUCCESS;
			} else {
				return "auth";
			}

		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
	}

	/**
	 * 描述: 保存用户个人信息<br/>
	 * 
	 * @return
	 * @return String 返回类型 创建时间：2016-9-11/下午4:06:36<br/>
	 * @author huayu
	 */
	public String saveInformation() {
		try {
			UserInfoSS userInfos = (UserInfoSS) getUser();

			userinfo.setRealName(userinfo.getRealName().trim());
			userinfo.setMobilNo(userinfo.getMobilNo().trim());
			userinfo.setEmail(userinfo.getEmail().trim());
			userinfo.setPhone(userinfo.getPhone().trim());
			userinfo.setAddress(userinfo.getAddress().trim());
			if (userinfo.getId() != null) {
				Userinfo tUserInfo = (Userinfo) HibernateDAO.findById(Userinfo.class, userinfo.getId());// 取回密码

				userinfo.setModifyTime(new Date());
				userinfo.setModifyUser(userInfos.getId());
				userinfo.setCreateTime(tUserInfo.getCreateTime());
				userinfo.setCreateUser(tUserInfo.getCreateUser());
				userinfo.setUserName(tUserInfo.getUserName());
				userinfo.setPassword(tUserInfo.getPassword());
				userinfo.setUserType(tUserInfo.getUserType());
				userinfo.setEnabled(tUserInfo.getEnabled());
				userinfo.setAccountNonExpired(tUserInfo.getAccountNonExpired());
				userinfo.setCredentialsNonExpired(tUserInfo.getCredentialsNonExpired());
				userinfo.setAccountNonLocked(tUserInfo.getAccountNonLocked());

				userinfo.setCompanyLevel1(tUserInfo.getCompanyLevel1());
				userinfo.setCompanyLevel2(tUserInfo.getCompanyLevel2());
				userinfo.setCompanyLevel3(tUserInfo.getCompanyLevel3());
				userinfo.setCompanyLevel4(tUserInfo.getCompanyLevel4());
				userinfo.setCompanyLevel(tUserInfo.getCompanyLevel());

				if (null != HibernateDAO.merge(userinfo)) {
					// 操作日志
					operation = "编辑";
					logContent = "个人信息编辑——用户名为：" + userinfo.getUserName() + "的用户信息";
					AddLog.addOperateLog(userInfos.getUserName(), userInfos.getRealName(), userInfos.getCompanyLevel() + "", logContent, operation);
					saveOk = true;
				} else {
					return ERROR;
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			return ERROR;
		}
	}

	/**
	 * 描述: 校验保存的用户个人信息<br/>
	 * 
	 * @return void 返回类型 创建时间：2016-9-11/下午4:07:19<br/>
	 * @author huayu
	 */
	public void validateSaveInformation() {

		UniquenessDetect uniquenessDetect = new UniquenessDetect();
		Boolean haveValidate = true;

		Object checkProperty = userinfo.getUserName();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "userName", "用户名", 28, true)) {
			haveValidate = false;
		}
		if (userinfo.getId() == null) {// 添加
			if (!uniquenessDetect.detectBySql("userinfo", "userName", checkProperty)) {
				haveValidate = false;
				this.addFieldError("userName", "用户名已存在!");
			}
		} else {
			if (!uniquenessDetect.detectExcludedIDBySql("userinfo", "userName", checkProperty, userinfo.getId())) {
				haveValidate = false;
				this.addFieldError("userName", "用户名已存在!");
			}
		}

		checkProperty = userinfo.getRealName();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "realName", "真实姓名", 22, true)) {
			haveValidate = false;
		}

		checkProperty = userinfo.getEmail();
		if (!ValidateUtil.submitCheck(this, "邮箱", checkProperty, "email", "电子邮箱", 80, true)) {
			haveValidate = false;
		}
		if (userinfo.getId() == null) {
			if (!uniquenessDetect.detectBySql("userinfo", "email", checkProperty)) {
				haveValidate = false;
				this.addFieldError("email", "邮箱已被使用!");
			}
		} else {
			if (!uniquenessDetect.detectExcludedIDBySql("userinfo", "email", checkProperty, userinfo.getId())) {
				haveValidate = false;
				this.addFieldError("email", "邮箱已被使用!");
			}
		}

		checkProperty = userinfo.getMobilNo();
		if (!ValidateUtil.submitCheck(this, "手机", checkProperty, "mobilNo", "手机号码", 11, true)) {
			haveValidate = false;
		}

		checkProperty = userinfo.getPhone();
		if (!ValidateUtil.submitCheck(this, "固话", checkProperty, "phone", "固定电话", 28, false)) {
			haveValidate = false;
		}

		checkProperty = userinfo.getAddress();
		if (!ValidateUtil.submitCheck(this, "字符", checkProperty, "address", "联系地址", 48, false)) {
			haveValidate = false;
		}

		if (haveValidate) {
			return;
		}
	}

	/**
	 * 描述: 密码修改<br/>
	 * 
	 * @return String 返回类型 创建时间：2016-9-11/下午4:43:06<br/>
	 * @author huayu
	 */
	public String editPassword() {
		UserInfoSS userInfos = (UserInfoSS) getUser();
		if (userInfos.getId() == id) {
			return SUCCESS;
		} else {
			return "auth";
		}

	}

	/**
	 * 描述: 保存对密码的修改<br/>
	 * 
	 * @return String 返回类型 创建时间：2016-9-11/下午7:55:24<br/>
	 * @author huayu
	 */
	public String savePassword() {
		try {
			UserInfoSS userInfos = (UserInfoSS) getUser();
			if (id != null) {
				Userinfo tUserInfo = (Userinfo) HibernateDAO.findById(Userinfo.class, id);
				tUserInfo.setPassword(EncryptMD5.MD5(passwordNew.trim()));
				tUserInfo.setModifyTime(new Date());
				tUserInfo.setModifyUser(userInfos.getId());
				if (null != HibernateDAO.merge(tUserInfo)) {
					// 操作日志
					operation = "编辑";
					logContent = "修改密码——用户名为：" + userInfos.getUserName() + "的用户修改密码";
					AddLog.addOperateLog(userInfos.getUserName(), userInfos.getRealName(), userInfos.getCompanyLevel() + "", logContent, operation);
				} else {
					return ERROR;
				}
			} else {
				addActionMessage("未找到用户，请重试!");
				return ERROR;
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("未能成功修改密码，请重试!");
			return ERROR;
		}
	}

	/**
	 * <li>功能描述：对用户密码校验。
	 */
	public void validateSavePassword() {
		Boolean haveValidate = true;
		Userinfo tUserInfo;
		try {
			tUserInfo = (Userinfo) HibernateDAO.findById(Userinfo.class, id);

			if (!tUserInfo.getPassword().equalsIgnoreCase(EncryptMD5.MD5(passwordOld.trim()))) {
				addFieldError("passwordOld", "原始密码有误，请重新输入");
				haveValidate = false;
			}
			if (null == passwordNew || passwordNew.equals("")) {
				addFieldError("passwordNew", "新密码不可以为空");
				haveValidate = false;
			} else if (passwordNew.trim().length() < 6 || passwordNew.trim().length() > 20) {
				addFieldError("passwordNew", "新密码长度应为6-20个字符");
				haveValidate = false;
			}
			if (!passwordNew.equals(passwordConfirm)) {
				addFieldError("passwordConfirm", "密码不一致");
				haveValidate = false;
			}
			if (haveValidate) {
				return;
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
		}
	}

	/**
	 * ajax校验原始密码
	 * 
	 * @return
	 */
	public String validateOldPassword() {
		try {
			String sBuffer = "";
			HttpServletResponse response = getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			sBuffer = "";
			Userinfo tUserInfo = (Userinfo) HibernateDAO.findById(Userinfo.class, id);
			if (!tUserInfo.getPassword().equalsIgnoreCase(EncryptMD5.MD5(passwordOld))) {
				sBuffer = "原始密码有误，请重新输入";
			} else {
				sBuffer = "";
			}
			response.getWriter().write(sBuffer);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
		}
		return null;
	}

	/**
	 * <li>功能描述：忘记密码
	 * 
	 * @return String
	 */
	public String forgetPassword() {
		return SUCCESS;
	}

	/**
	 * <li>功能描述：忘记密码，验证用户信息
	 * 
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public String confirmUserInfo() {
		// 给注册用户发送密码邮件
		SendMail sendMail = new SendMail();
		int pushState;
		try {
			String queryString = "delete from userIdentity where userName = '" + userName + "'";
			HibernateDAO.batchBySql(queryString);

			String userIdentityTemp = UUID.randomUUID().toString();// 用户身份标识的串码

			pushState = sendMail.sendAction(SysConfig.getConfigData("Email", "value", "电子邮箱账号读取错误"), SysConfig.getConfigData("Email", "password", "电子邮箱密码读取错误"), email, "修改登录密码",
					"修改登录密码：" + " 请点击 <a href='http://" + SysConfig.getIPAndPort() + "/jjpt/user/modifyPassword?userName=" + userName + "&userIdentity=" + userIdentityTemp
							+ "'>修改密码</a>", "html");

			if (pushState == 0) {
//				Useridentity tuIdentity = new Useridentity();
//				tuIdentity.setUserName(userName);
//				tuIdentity.setStringCode(userIdentityTemp);
//				if (HibernateDAO.merge(tuIdentity) == null) {
//					pushState = 2;
//				}
			}
		} catch (Exception e) {
			pushState = 1;
			log.error(e);// 记录错误日志
		}
		if (pushState != 0) {// 此处不区分是文件没发出去还是串码存库失败
			addActionMessage("修改密码出错，请点击[返回]后刷新页面，如不能解决请联系管理员!");
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * <li>功能描述：忘记密码，验证用户信息
	 */
	public void validateConfirmUserInfo() {
		Boolean haveValidate = true;
		try {// 重复提交表单时，先过校验方法然后才是token过滤器，所以还没有拦截住重复提交就已经开始校验了，有可能造成session为空，报错。
			String contextCode = ActionContext.getContext().getSession().get("verifyCode").toString().trim();
			String verifyCode = getRequest().getParameter("verifyCode");// 校验码
			if (!verifyCode.equals(contextCode)) {
				this.addFieldError("authVerifyCode", "验证码不正确");
				getRequest().getSession().removeAttribute("verifyCode");// 清除session中验证码
				haveValidate = false;
			} else {
				getRequest().getSession().removeAttribute("verifyCode");// 清除session中验证码
			}
		} catch (Exception e) {
			haveValidate = false;
		}

		if (null == userName || userName.equals("")) {
			addFieldError("authuserName", "账号不可以为空");
			haveValidate = false;
		}
		if (null == email || email.equals("")) {
			addFieldError("authEmail", "电子邮箱不可以为空");
			haveValidate = false;
		}
		try {
			Userinfo tUserInfo = (Userinfo) userInfoDAO.findByUserName(userName).get(0);
			if (!email.equals(tUserInfo.getEmail())) {
				addFieldError("authEmail", "输入的电子邮箱有误");
				haveValidate = false;
			}
		} catch (Exception e) {
			addFieldError("authuserName", "您输入的注册信息有误");
			haveValidate = false;
		}
		if (haveValidate) {
			return;
		}
	}

	/**
	 * <li>功能描述：修改密码（忘记密码）
	 * 
	 * @return String
	 */
	public String modifyPassword() {
		return SUCCESS;
	}

	/**
	 * <li>功能描述：保存密码（忘记密码）
	 * 
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public String saveModifyPassword() {
		try {
			Userinfo tUserInfo;
			String sqlString = "select * from userinfo where enabled=0 and userName='" + userName + "'";
			List temList = HibernateDAO.findBySql(sqlString, Userinfo.class);
			if (temList != null && temList.size() > 0) {
				tUserInfo = (Userinfo) temList.get(0);
				tUserInfo.setModifyTime(new Date());
				tUserInfo.setModifyUser(tUserInfo.getId());
				tUserInfo.setPassword(EncryptMD5.MD5(passwordNew.trim()));
				if (HibernateDAO.merge(tUserInfo) != null) {
					String queryString = "delete from userIdentity where userName = '" + userName + "'";
					HibernateDAO.batchBySql(queryString);// 删除以前保存的串码
				} else {
					addActionMessage("未能成功修改密码，请重试!");
					return ERROR;
				}
			} else {
				addActionMessage("未能成功修改密码，请重试!");
				return ERROR;
			}

			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("未能成功修改密码，请重试!");
			return ERROR;
		}
	}

	/**
	 * <li>功能描述：对用户密码校验。
	 */
	@SuppressWarnings("rawtypes")
	public void validateSaveModifyPassword() {
		try {
			String stringCode = "";
//			String queryString = " select * from userIdentity where userName = '" + userName + "'";
//			List tempList = HibernateDAO.findBySql(queryString, Useridentity.class);
//			if (tempList != null && tempList.size() > 0) {
//				stringCode = ((Useridentity) tempList.get(0)).getStringCode();
//			}
			if (stringCode != "") {
				if (!stringCode.equals(userIdentity)) {
					addFieldError("authPassword", "用户身份无法识别，请重新找回密码");
					return;
				}
			} else {
				addFieldError("authPassword", "用户身份无法识别，请重新找回密码");
				return;
			}

			Boolean haveValidate = true;
			if (null == passwordNew || passwordNew.equals("")) {
				addFieldError("passwordNew", "新密码不可以为空");
				haveValidate = false;
			} else if (passwordNew.trim().length() < 6 || passwordNew.trim().length() > 20) {
				addFieldError("passwordNew", "新密码长度应为6-20个字符");
				haveValidate = false;
			}
			if (!passwordNew.equals(passwordConfirm)) {
				addFieldError("passwordConfirm", "密码不一致");
				haveValidate = false;
			}
			if (haveValidate) {
				return;
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
		}
	}

	/**
	 * 描述: 根据父节点ID寻找所有子节点<br/>
	 * 
	 * @return
	 * @return String 返回类型 创建时间：2016-9-15/上午11:26:42<br/>
	 * @author huayu
	 */
	public String showManagerZoneList() {
		try {
			String sBuffer = "";
//			List<Organize> zoneList = studentUserDAO.findOrganizesByPid(zoneId);
//			getResponse().setContentType("text/html");
//			getResponse().setCharacterEncoding("utf-8");
//			sBuffer = "";
//			if (null == zoneList || zoneList.size() == 0) {
//			} else {
//				for (Iterator<Organize> iterator = zoneList.iterator(); iterator.hasNext();) {
//					Organize obj = iterator.next();
//					sBuffer = sBuffer + "," + obj.getId() + "," + obj.getOrgName();
//				}
//				sBuffer = sBuffer.substring(1);
//			}
			getResponse().getWriter().write(sBuffer);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
		}
		return null;
	}

	public List<Userinfo> getUserList() {
		return userList;
	}

	public void setUserList(List<Userinfo> userList) {
		this.userList = userList;
	}

	public Userinfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCompanyNameCX() {
		return companyNameCX;
	}

	public void setCompanyNameCX(String companyNameCX) {
		this.companyNameCX = companyNameCX;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public String getRoleNameCX() {
		return roleNameCX;
	}

	public void setRoleNameCX(String roleNameCX) {
		this.roleNameCX = roleNameCX;
	}

	public String getAllPageCKB() {
		return allPageCKB;
	}

	public void setAllPageCKB(String allPageCKB) {
		this.allPageCKB = allPageCKB;
	}

	public String getSelectCKB() {
		return selectCKB;
	}

	public void setSelectCKB(String selectCKB) {
		this.selectCKB = selectCKB;
	}

	public List<Object> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Object> roleList) {
		this.roleList = roleList;
	}

	public String getUserNameCX() {
		return userNameCX;
	}

	public void setUserNameCX(String userNameCX) {
		this.userNameCX = userNameCX;
	}

	public String getEmailCX() {
		return emailCX;
	}

	public void setEmailCX(String emailCX) {
		this.emailCX = emailCX;
	}

	public String getRealNameCX() {
		return realNameCX;
	}

	public void setRealNameCX(String realNameCX) {
		this.realNameCX = realNameCX;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public boolean isSaveOk() {
		return saveOk;
	}

	public void setSaveOk(boolean saveOk) {
		this.saveOk = saveOk;
	}

//	public List<Organize> getFirstLevelList() {
//		return firstLevelList;
//	}
//
//	public void setFirstLevelList(List<Organize> firstLevelList) {
//		this.firstLevelList = firstLevelList;
//	}
//
//	public List<Organize> getSecondLevelList() {
//		return secondLevelList;
//	}
//
//	public void setSecondLevelList(List<Organize> secondLevelList) {
//		this.secondLevelList = secondLevelList;
//	}
//
//	public List<Organize> getThirdLevelList() {
//		return thirdLevelList;
//	}
//
//	public void setThirdLevelList(List<Organize> thirdLevelList) {
//		this.thirdLevelList = thirdLevelList;
//	}
//
//	public List<Organize> getFourthLevelList() {
//		return fourthLevelList;
//	}
//
//	public void setFourthLevelList(List<Organize> fourthLevelList) {
//		this.fourthLevelList = fourthLevelList;
//	}

	public String getFirstIdCX() {
		return firstIdCX;
	}

	public void setFirstIdCX(String firstIdCX) {
		this.firstIdCX = firstIdCX;
	}

	public String getSecondIdCX() {
		return secondIdCX;
	}

	public void setSecondIdCX(String secondIdCX) {
		this.secondIdCX = secondIdCX;
	}

	public String getThirdIdCX() {
		return thirdIdCX;
	}

	public void setThirdIdCX(String thirdIdCX) {
		this.thirdIdCX = thirdIdCX;
	}

	public String getFourthIdCX() {
		return fourthIdCX;
	}

	public void setFourthIdCX(String fourthIdCX) {
		this.fourthIdCX = fourthIdCX;
	}

	public String getLevelIdCX() {
		return levelIdCX;
	}

	public void setLevelIdCX(String levelIdCX) {
		this.levelIdCX = levelIdCX;
	}
}
