package com.ningxun.team.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.team.vo.Teaminfo;
import com.ningxun.wxuserinfo.vo.WXUser;

@SuppressWarnings("all")
public class TeaminfoDao extends HibernateDAO {

	private String tagName[];//json对象的key数组
	
	/**
	 * 获取当前登录人创建的球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param uid 用户id
	 * @return
	 * @throws Exception 
	 */
	public List<Object[]> getCreateTeamByUid(Integer uid) throws Exception {
		
		String sql = "SELECT t.id, t.name,"
				+ " (SELECT u2.nickname FROM user u2 WHERE  u2.id = t.captainId) nickname,t.iconNewName,t.teamDeclaration "
				+ " FROM teaminfo t "
				+ " LEFT JOIN user_team ut ON ut.tid = t.id "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND (ut.position = 1) AND u.delState = 0 AND t.delState = 0 "
				+ " ORDER BY CONVERT (t.name USING gbk) ASC";
		
		return findBySql(sql);
	}
	
	/**
	 * 获取用户id管理的球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param id 用户id
	 * @return
	 */
	public List<Object[]> getManageTeamByUid(Integer uid) throws Exception {
		
		String sql = "SELECT t.id, t.name,"
				+ " (SELECT u2.nickname FROM user u2 WHERE u2.id = t.captainId) nickname,t.iconNewName,t.teamDeclaration "
				+ " FROM teaminfo t "
				+ " LEFT JOIN user_team ut ON ut.tid = t.id "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND ut.position IN (2,3) AND u.delState = 0 AND t.delState = 0 "
				+ " ORDER BY CONVERT (t.name USING gbk) ASC";
		
		return findBySql(sql);
	}

	/**
	 * 获取当前登录人加入的球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param uid 用户id
	 * @return
	 */
	public List<Object[]> getJoinTeamByUid(Integer uid) throws Exception {
		
		String sql = "SELECT t.id, t.name,"
				+ " (SELECT u2.nickname FROM user u2 WHERE  u2.id = t.captainId) nickname,t.iconNewName,t.teamDeclaration "
				+ " FROM teaminfo t "
				+ " LEFT JOIN user_team ut ON ut.tid = t.id "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND ut.position = 4 AND u.delState = 0 AND t.delState = 0 "
				+ " ORDER BY CONVERT (t.name USING gbk) ASC";
		
		return findBySql(sql);
	}

	/**
	 * 根据球队id获取球队详情
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public Object[] getTeaminfoDetail(Integer tid) throws Exception{
	
		String sql = "SELECT t.id,t.createTime,u.nickname,t.teamDeclaration,t.introduce,t.iconNewName,t.province,t.city,t.regulations,t.name FROM teaminfo t LEFT JOIN user u ON t.captainId = u.id WHERE t.id = "+tid+" AND t.delState = 0";
		List<Object[]> list = findBySql(sql);
		
		return (list != null && list.size()> 0) ? list.get(0) : null;
	}
	
	/**
	 * 根据球队名称后置模糊查询球队列表
	 * @author hujian
	 * @date 2017年7月27日
	 * @version 1.0
	 * @param teamName 球队名称
	 * @return
	 */
	public List<Object[]> queryTeaminfoList(String teamName) throws Exception{
		String sql = "SELECT t.id,t.name,u.nickname,t.iconNewName "
				+ " FROM teaminfo t LEFT JOIN user u ON t.captainId = u.id "
				+ " WHERE t.name LIKE '"+teamName.trim()+"%' AND t.delState = 0 "
				+ " ORDER BY t.id DESC";
		return findBySql(sql);
	}

	/**
	* 描述: 比赛根据球队名称后置模糊查询球队列表（过滤球队本身）<br/>
	*
	* @return List<Object[]> 返回类型
	*
	* 创建时间：2017-9-26/下午2:40:11<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
public List<Object[]> queryTeaminfoListR(String teamName,int teamId) throws Exception{
		String sql = "SELECT t.id,t.name,u.nickname,t.iconNewName "
				+ " FROM teaminfo t LEFT JOIN user u ON t.captainId = u.id "
				+ " WHERE t.name LIKE '"+teamName.trim()+"%' AND t.delState = 0 AND t.id != "+teamId
				+ " ORDER BY CONVERT (t.name USING gbk) ASC";
		return findBySql(sql);
	}	
	
	/**
	 * 根据球队名查询球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param teamName 球队名称
	 * @return
	 */
	public List<Object[]> queryTeaminfoList(String teamName,int pageNo,int pageSize) throws Exception{
		String sql = getQueryTeaminfoListSql(teamName);
		return findBySql(sql, pageNo, pageSize);
	}
	
	/**
	 * 获取查询球队列表的sql
	 * @author hujian
	 * @date 2017年7月27日
	 * @version 1.0
	 * @param teamName 球队名称
	 * @return
	 */
	public String getQueryTeaminfoListSql(String teamName) {
		String sql = "SELECT t.id,t.name,u.nickname,t.iconNewName "
				+ " FROM teaminfo t LEFT JOIN user u ON t.captainId = u.id "
				+ " WHERE t.isOpen = 0 AND t.name LIKE '%"+teamName.trim()+"%' AND t.delState = 0 "
				+ " ORDER BY t.id DESC";
		tagName = new String[] { "id", "name","nickname","iconNewName"};
		return sql;
	}
	
	/**
	 * 根据省份和城市查询用户木有加入过的球队列表
	 * @author hujian
	 * @date 2017年8月8日
	 * @version 1.0
	 * @param uid 用户id
	 * @param province 省份
	 * @param city 城市
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Object[]> recommenderTeaminfoList(Integer uid, String province, String city,int pageNo,int pageSize) throws Exception{
		String sql = getRecommenderTeaminfoListSql( uid, province, city);
		return findBySql(sql, pageNo, pageSize);
	}
	
	/**
	 * 获取根据地理位置推荐球队列表的sql
	 * @author hujian
	 * @date 2017年8月8日
	 * @version 1.0
	 * @param uid 用户id
	 * @param province 省份
	 * @param city 城市
	 * @return
	 */
	public String getRecommenderTeaminfoListSql(Integer uid, String province, String city) {
		//推荐的球队为登录用户木有加入过的球队
		String sql = "SELECT t.id,t.name,u.nickname,t.iconNewName "
				+ " FROM teaminfo t LEFT JOIN user u ON t.captainId = u.id "
				+ " WHERE t.isOpen = 0 AND (t.province LIKE '"+province+"%' OR t.city LIKE '"+city+"%') AND t.delState = 0 AND t.id NOT IN (SELECT DISTINCT ut.tid FROM user_team ut WHERE ut.uid = "+uid+")"
				+ " ORDER BY t.id DESC";
		tagName = new String[] { "id", "name","nickname","iconNewName"};
		return sql;
	}
	
	/**
	 * 获取用户木有加入过的球队列表
	 * @author hujian
	 * @date 2017年8月8日
	 * @version 1.0
	 * @version 1.0
	 * @param uid 用户id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getTeaminfoList(Integer uid,int pageNo,int pageSize) throws Exception {
		String sql = "SELECT t.id,t.name,u.nickname,t.iconNewName "
				+ " FROM teaminfo t LEFT JOIN user u ON t.captainId = u.id "
				+ " WHERE t.isOpen = 0 AND t.delState = 0 AND t.id NOT IN (SELECT DISTINCT ut.tid FROM user_team ut WHERE ut.uid = "+uid+")"
				+ " ORDER BY t.id DESC";
		return findBySql(sql, pageNo, pageSize);
	}
	
	/**
	 * 根据球队id获取成员列表
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public List<Object[]> getMemberListByTid(Integer tid) throws Exception{
		
		String sql = "SELECT DISTINCT u.id,u.nickname,u.headPortraitNew,p.positionName,ut.clothesNum,ut.remakeName,u.headPortrait,ut.playerPosition,IFNULL(ut.remakeName,u.nickname) AS finalName "
				+ " FROM user u "
				+ " LEFT JOIN user_team ut ON u.id = ut.uid "
				+ " LEFT JOIN teaminfo t ON t.id = ut.tid "
				+ " LEFT JOIN position p ON p.id = ut.position "
				+ " WHERE t.id = "+tid+" AND u.delState = 0 AND t.delState = 0 "
				+ " ORDER BY p.id ASC, CONVERT (finalName USING gbk) ASC";
		return findBySql(sql);
	}
	
	/**
	 * 根据球队id获取管理员列表
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public List<WXUser> getAdminListByTid(Integer tid) throws Exception{
		String sql = "SELECT DISTINCT u.* "
				+ " FROM user u "
				+ " LEFT JOIN user_team ut ON u.id = ut.uid "
				+ " LEFT JOIN teaminfo t ON t.id = ut.tid "
				+ " WHERE t.id = "+tid+" AND (ut.position BETWEEN 1 AND 3) AND u.delState = 0 AND t.delState = 0";
		return findBySql(sql,WXUser.class);
	}
	
	/**
	 * 根据球队id获取队长昵称和球队名称
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public Object[] getCaptainNameAndTeamNameByTid(Integer tid) throws Exception{
		String sql = "SELECT DISTINCT u.nickname,t.name "
				+ " FROM user u "
				+ " LEFT JOIN user_team ut ON u.id = ut.uid "
				+ " LEFT JOIN teaminfo t ON t.id = ut.tid "
				+ " WHERE t.id = "+tid+" AND ut.position = 1 AND u.delState = 0 AND t.delState = 0";
		List<Object[]> list = findBySql(sql);
		return (list != null && list.size()>0) ? list.get(0) : null ;
	}

	/**
	 * 根据球队名称和用户id校验是否存在该球队
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @param id 主键
	 * @param uid 用户id
	 * @param teamName 球队名称
	 * @return true 存在  ,  false 不存在
	 * @throws Exception 
	 */
	public boolean checkTeamName(Integer id,int uid, String teamName) throws Exception {
		if (id == null) {
			id = 0;
		}
		String sql = "SELECT id FROM teaminfo WHERE captainId = "+uid+" AND name = '"+teamName+"' AND id != "+id+" AND delState = 0";
		List<Teaminfo> list = findBySql(sql, Teaminfo.class);
		return (list != null && list.size() != 0);
	}
	
	public List findAllTeamId() throws Exception{
		String sql = "SELECT t.id FROM teaminfo AS t WHERE t.delState = 0";
		return findBySql(sql);
	}
	
	public String[] getTagName() {
		return tagName;
	}

	public void setTagName(String[] tagName) {
		this.tagName = tagName;
	}

}
