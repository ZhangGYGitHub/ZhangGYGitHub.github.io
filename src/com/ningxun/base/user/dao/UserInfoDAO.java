package com.ningxun.base.user.dao;

import java.util.ArrayList;
import java.util.List;

import com.ningxun.base.user.vo.Userinfo;
import com.ningxun.common.HibernateDAO;
import com.ningxun.security.action.UserInfoSS;

public class UserInfoDAO extends HibernateDAO {

	private HibernateDAO hibernateDAO = new HibernateDAO();

	private int resultRows; // 总记录数
	private int pageCount; // 总页数
	private List<Userinfo> userList;
//	private List<Organize> companyList;
	private List<Integer> departmentIdList = new ArrayList<Integer>();
	public static final String USER_NAME = "userName";

	/**
	 * 描述: 查看角色归属的用户<br/>
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param roleId
	 * @param realNameCX
	 * @param userNameCX
	 * @return
	 * @return List<Resources> 返回类型 创建时间：2016年7月18日/下午5:42:41<br/>
	 * @author Administrator
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Userinfo> findUserList(int pageNo, int pageSize, Integer roleId, String realNameCX, String userNameCX) throws Exception {
		String appendString = " and realName like ?  and userName like ?";

		if (realNameCX == null) {
			realNameCX = "";
		}
		if (userNameCX == null) {
			userNameCX = "";
		}
		List<String> paramList = new ArrayList<String>();
		paramList.add(realNameCX.trim());
		paramList.add(userNameCX.trim());

		String queryString = "select ui.id,realName,userName,mobilNo,address,email from Userinfo ui" + " left join userInforole ur on ui.id = ur.userInfoID  "
				+ " where ui.enabled = 0 and ur.roleID = " + roleId + appendString + " order by id desc";

		List<Userinfo> reslutList = hibernateDAO.findBySql(queryString, Userinfo.class, pageNo, pageSize, paramList);
		resultRows = hibernateDAO.getResultRows();
		pageCount = hibernateDAO.getPageCount();
		return reslutList;
	}

	@SuppressWarnings("unchecked")
	public void findOrganize(Integer departmentId) throws Exception {

//		if (departmentId != null) {
//			departmentIdList.add(departmentId);
//			String sqlString = "select id,pid from organize where pid = " + departmentId;
//			List<Organize> tmp = HibernateDAO.findBySql(sqlString, Organize.class);
//			if (tmp.size() > 0) {
//				for (Organize o : tmp) {
//					findOrganize(o.getId());
//				}
//			}
//		}
	}

	@SuppressWarnings("unchecked")
	public List<Userinfo> findUserList(UserInfoSS userInfoSS, String userNameCX, String realNameCX, String companyLevelCX, int pageNo, int pageSize) throws Exception {
		String sqlString = "";
		String appendString = "";// 拼接sql的字符串
		if (userNameCX == null) {
			userNameCX = "";
		}
		if (realNameCX == null) {
			realNameCX = "";
		}
		List<String> paramList = new ArrayList<String>();
		paramList.add(userNameCX.trim());
		paramList.add(realNameCX.trim());

		if (!(null == companyLevelCX || companyLevelCX == "")) {
			appendString = " and userinfo.companyLevel = '" + companyLevelCX + "'";
		}

		if (userInfoSS.getUserType() == 2) {
			sqlString = "select userinfo.id,userinfo.userName,userinfo.realName,userinfo.mobilNo,userinfo.email,userinfo.userType from userinfo LEFT JOIN organize on organize.id = userinfo.companyLevel where userinfo.enabled = 0 "
					+ appendString + " and userinfo.userName like ? and userinfo.realName like ? ";
//			sqlString = "select userinfo.id,userinfo.userName,userinfo.realName,userinfo.mobilNo,userinfo.email,userinfo.userType,organize.orgName as companyLevelName from userinfo LEFT JOIN organize on organize.id = userinfo.companyLevel where userinfo.enabled = 0 "
//					+ appendString + " and userinfo.userName like ? and userinfo.realName like ? ";
		}

		userList = hibernateDAO.findBySql(sqlString, Userinfo.class, pageNo, pageSize, paramList);
		resultRows = hibernateDAO.getResultRows();
		pageCount = hibernateDAO.getPageCount();
		return userList;
	}

//	@SuppressWarnings({ "unchecked" })
//	public List<Organize> findDepartmentList(String departmentId) throws Exception {
//		String sqlString = "select * from organize where pid=" + departmentId;
//		List<Organize> departmentList = HibernateDAO.findBySqlStars(sqlString, Organize.class);
//		return departmentList;
//	}
//
//	@SuppressWarnings({ "unchecked", })
//	public List<Organize> findCompanyList() throws Exception {
//		String sqlString = "select organize.id,organize.companyId,organize.orgName,organize.delState,organize.orgMail,organize.orgName from organize WHERE (pid=1 or pid=0) and (organize.orgType=1 or organize.orgType=0) order by pid desc";
//		companyList = HibernateDAO.findBySql(sqlString, Organize.class);
//		return companyList;
//	}

	@SuppressWarnings({ "rawtypes", })
	public List findByUserName(Object userName) throws Exception {
		String sqlString = "select userName,email from userinfo where enabled=0 and userName='" + userName + "'";
		List temList = HibernateDAO.findBySql(sqlString, Userinfo.class);
		return temList;
	}

	/**
	 * 描述: 根据ID查找管理员信息<br/>
	 * 
	 * @param id
	 * @return Userinfo 返回类型 创建时间：2016-9-25/下午5:48:45<br/>
	 * @author huayu
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Userinfo findByUserId(int id) throws Exception {

		String sqlString = "  select userinfo.id,userinfo.userName,userinfo.realName,userinfo.mobilNo,userinfo.email,userinfo.userType,userinfo.companyLevel,organize.orgName as companyLevelName,userinfo.sex,userinfo.address,userinfo.phone,userinfo.accountNonLocked from userinfo LEFT JOIN organize on organize.id = userinfo.companyLevel"
				+ " where userinfo.id = " + id;
		List tList = HibernateDAO.findBySql(sqlString, Userinfo.class);
		return (Userinfo) tList.get(0);
	}

	public int getResultRows() {
		return resultRows;
	}

	public void setResultRows(int resultRows) {
		this.resultRows = resultRows;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}