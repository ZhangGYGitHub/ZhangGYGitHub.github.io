package com.ningxun.notice.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.team.vo.UserTeam;

/**
* <li>@ClassName: NoticeDao<br/>
* <li>创建时间：2017年7月23日 下午3:32:42<br/>
*
* 描述:这里用一句话描述这个类的作用<br/>
* @author 梦强
*/
public class NoticeDao extends HibernateDAO{
	
	private String tagName[];//json对象的key数组
	private String tagName1[];
	
	/**
	 * 
	* 描述: 根据uid查询所有通知列表<br/>
	* @param uid 用户id
	* @return List 返回类型
	*
	* 创建时间：2017年7月23日/下午3:32:52<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findUserNotice(Integer uid) throws Exception{
		String sql ="SELECT n.id, n.title, n.content, n.createTime, n.nickname, t.`name` AS tName, " +
				"t.iconNewName FROM USER AS u LEFT JOIN user_team AS ut ON ut.uid = u.id LEFT JOIN " +
				"notice_team AS nt ON nt.tid = ut.tid LEFT JOIN notice AS n ON n.id = nt.nid LEFT " +
				"JOIN teaminfo AS t ON t.id = nt.tid WHERE n.delState = 0 AND u.delState = 0 AND " +
				"t.delState = 0 AND n.type = 1 AND	u.id = "+uid+" ORDER BY n.createTime DESC";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 根据uid查询通知<br/>
	*
	* @return List 返回类型
	*
	* 创建时间：2017-8-10/下午9:58:11<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findUserNotice(Integer uid,int pageNo,int pageSize) throws Exception{
		String sql =getUNoticeListsql(uid);
		return findBySql(sql, pageNo, pageSize);
	}
	/**
	 * 
	* 描述: 根据用户id获取通知列表sql<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-8-10/下午9:56:45<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String getUNoticeListsql(Integer uid){
		String sql ="SELECT n.id, n.title, n.content, n.createTime, n.nickname, t.`name` AS tName, " +
				"t.iconNewName,ut.position FROM USER AS u LEFT JOIN user_team AS ut ON ut.uid = u.id LEFT JOIN " +
				"notice_team AS nt ON nt.tid = ut.tid LEFT JOIN notice AS n ON n.id = nt.nid LEFT " +
				"JOIN teaminfo AS t ON t.id = nt.tid WHERE n.delState = 0 AND u.delState = 0 AND " +
				"t.delState = 0 AND n.type = 1 AND	u.id = "+uid+" ORDER BY n.createTime DESC";
		tagName1 = new String[] { "id", "title","content","createTime","nickname","tName","iconNewName","position"};
		return sql;
	}
	
	/**
	 * 
	* 描述: 根据tid查询该队的通知<br/>
	* @param tid 球队id
	* @return List 返回类型
	*
	* 创建时间：2017年7月26日/下午3:51:48<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findTeamNotice(Integer tid) throws Exception{
		String sql ="SELECT n.id, n.title, n.content, n.createTime, n.nickName, "
				+ "p.positionName,n.type FROM notice_team AS nt LEFT JOIN notice AS "
				+ "n ON n.id = nt.nid LEFT JOIN position AS p ON p.id = n.position "
				+ "WHERE nt.tid = "+tid+" AND n.delState = 0 ORDER BY n.id DESC";
		return findBySql(sql);
	}
	
	/**
	 * 根据球队id查询所有通知列表
	 * @author hujian
	 * @date 2017年7月28日
	 * @version 1.0
	 * @param tid 球队id
	 * @param pageNo 当前页码
	 * @param pageSize 每页条数
	 * @return
	 * @throws Exception
	 */
	public List findTeamNotice(Integer tid,Integer pid,int pageNo,int pageSize) throws Exception{
		String sql =getNoticeListSql(tid,pid);
		return findBySql(sql, pageNo, pageSize);
	}
	
	/**
	 * 根据球队id获取通知列表sql
	 * @author hujian
	 * @date 2017年7月28日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public String getNoticeListSql(Integer tid,Integer pid) {
		
		String sql = "";
		if (pid == 4) {
			sql = "SELECT n.id, n.title, n.content, n.createTime," +
					"n.nickName ,p.positionName,n.type FROM notice_team AS " +
					"nt LEFT JOIN notice AS n ON n.id = nt.nid LEFT " +
					"JOIN position AS p ON p.id = n.position WHERE nt.tid =" +tid +
					"  AND n.delState = 0 AND n.type = 1 ORDER BY n.id DESC";
		}else {
			sql = "SELECT n.id, n.title, n.content, n.createTime," +
					"n.nickName ,p.positionName,n.type FROM notice_team AS " +
					"nt LEFT JOIN notice AS n ON n.id = nt.nid LEFT " +
					"JOIN position AS p ON p.id = n.position WHERE nt.tid =" +tid +
					"  AND n.delState = 0 ORDER BY n.id DESC";
		}
		tagName = new String[] { "id", "title","content","createTime","nickname","position","type"};
		return sql;
	}
	
	/**
	 * 
	* 描述: 根据用户id查询管理和创建队伍列表<br/>
	* 
	* @param uid 用户id
	* @return List 返回类型
	*
	* 创建时间：2017年7月23日/下午3:33:07<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findTeamList(Integer uid) throws Exception{
		String sql = "SELECT tf.id, tf. NAME, u.nickname FROM "
				+ "user_team AS ut LEFT JOIN teaminfo AS tf ON "
				+ "tf.id = ut.tid LEFT JOIN `user` AS u on u.id "
				+ "= tf.captainId WHERE ut.position <> 4 AND "
				+ "tf.delState = 0 AND ut.uid = "+uid;
		return findBySql(sql);
	}
	/**
	 * 根据用户id查询两条最新的通知
	 * @author hujian
	 * @date 2017年7月23日
	 * @version 1.0
	 * @param uid 用户id
	 * @return
	 * @throws Exception 
	 */
	public List<Object[]> getTwoNoticeByUid(Integer uid) throws Exception {
		String sql = "SELECT n.id,n.title,n.createTime,n.picNewName,t.id AS tid "
				+ " FROM notice n "
				+ " LEFT JOIN notice_team nt ON nt.nid = n.id "
				+ " LEFT JOIN teaminfo t ON t.id = nt.tid "
				+ " LEFT JOIN user_team ut ON ut.tid = t.id "
				+ " LEFT JOIN user u ON u.id = ut.uid WHERE u.id = "+uid+" AND n.delState = 0 AND t.delState = 0 AND u.delState = 0 "
				+ " ORDER BY n.id DESC "
				+ " LIMIT 0,2";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 根据tid查询整个球队成员的openid<br/>
	* 
	* 
	* @param tid 球队id
	* @return List 返回类型
	*
	* 创建时间：2017年7月26日/下午3:51:48<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List<String> findUOpenid(Integer tid) throws Exception{
		String sql = "SELECT u.openId FROM  `user`  AS u LEFT JOIN  "
				+ "user_team AS ut ON ut.uid = u.id WHERE ut.tid  "
				+ " =  "+tid+" AND u.delState = 0 AND u.accountNonExpired  "
				+ "= 0 AND u.credentialsNonExpired = 0 AND u.accountNonLocked  "
				+ "= 0 AND u.enabled = 0";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 根据uid和tid查询该球员在球队中的备注名称<br/>
	*
	* @return List<UserTeam> 返回类型
	*
	* 创建时间：2017-10-10/下午4:47:03<br/>
	* @author 纪梦强  
	* @version V1.0
	 */
	public UserTeam findRemarkName(Integer uid , Integer tid) throws Exception{
		String queryString = "SELECT * FROM user_team WHERE uid = "+uid+" AND tid = "+tid;
		List<UserTeam> resultList = findBySql(queryString, UserTeam.class);
		return resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
	}
	
	public int updateNoticeType(Integer nid) throws Exception{
		String sql = "UPDATE notice n SET n.type = 1 WHERE n.id= "+nid;
		return executeBySql(sql);
	}

	public String[] getTagName() {
		return tagName;
	}

	public void setTagName(String[] tagName) {
		this.tagName = tagName;
	}
	public String[] getTagName1() {
		return tagName1;
	}
	public void setTagName1(String[] tagName1) {
		this.tagName1 = tagName1;
	}

	
}
