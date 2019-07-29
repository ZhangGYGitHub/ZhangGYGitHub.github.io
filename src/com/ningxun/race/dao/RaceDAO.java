package com.ningxun.race.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.race.vo.Race;
import com.ningxun.team.vo.Teaminfo;
import com.ningxun.team.vo.UserTeam;
import com.sun.net.httpserver.Authenticator.Success;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: RaceDAO.java<br/>
 *<li>创建时间: 2017-7-20 上午9:35:57<br/>
 *
 *@author 高佳伟	
 *@version [v1.00]
 */
@SuppressWarnings("all")
public class RaceDAO extends HibernateDAO {

	private String tagName[];//json对象的key数组
	private String tagName1[];
	
	/**
	 * 根据球队id查询比赛列表
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 */
	public  List<Object[]> findRaceListByTid(Integer tid,int pageNo,int pageSize) throws Exception{
		
		String sql = getRaceListSql(tid);
		return findBySql(sql, pageNo, pageSize);
	}
	
	
	/**
	 * 获取查询比赛列表sql
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 * 比赛双方可见
	 */
	public String getRaceListSql(Integer tid) {
		String sql = "SELECT r.id,r.raceName,r.place,r.startTime,r.endTime,r.signType,r.signTime,r.isRelease,r.createUser,r.tid,r.myScore,r.opScore,(SELECT COUNT(s.signUp) num FROM sign s WHERE s.signUp = 1 AND s.raceortrain=2 AND s.raceortrainId = r.id AND s.delState=0) number "
				+ " FROM race r "
				+ " LEFT JOIN teaminfo t ON r.tid = t.id "
				+ " WHERE (t.id = "+tid+" OR r.oppoId = "+tid+") AND r.delState = 0 AND t.delState = 0 "
				+ " ORDER BY r.startTime DESC";
		tagName = new String[] { "id", "raceName","place","startTime","endTime","signType","signTime","isRelease","createUser","tid","myScore","opScore","number"};
		return sql;
	}
	
	
	public List<Race> finddetail(Integer id) throws Exception {
		String sql = "SELECT r.* from race r LEFT JOIN teaminfo t on r.tid = t.id LEFT JOIN teaminfo t2 on r.oppoId = t2.id "
				  +   "	WHERE r.id = "+id;
		
		return HibernateDAO.findBySql(sql,Race.class);
	}
	
	public List<Teaminfo> finddetail1(Integer id) throws Exception {
		String sql = "SELECT t.* from race r LEFT JOIN teaminfo t on r.tid = t.id LEFT JOIN teaminfo t2 on r.oppoId = t2.id "
				  +   "	WHERE r.id = "+id;
		
		return HibernateDAO.findBySql(sql,Teaminfo.class);
	}
	public List<Teaminfo> finddetail2(Integer id) throws Exception {
		String sql = "SELECT t2.* from race r LEFT JOIN teaminfo t on r.tid = t.id LEFT JOIN teaminfo t2 on r.oppoId = t2.id "
				  +   "WHERE r.id = "+id;
		
		return HibernateDAO.findBySql(sql,Teaminfo.class);
	}
//	public String getname(Integer id){
//		String sql = "SELECT r.raceName,r.myScore,r.opScore,r.signType,r.clothescolour,r.type,r.endTime,r.id,r.place,r.league,r.remark,r.signTime,r.startTime,r.waste,t.`name`,t2.`name`"
//			  +	"from race r LEFT JOIN teaminfo t on r.tid = t.id LEFT JOIN teaminfo t2 on r.oppoId = t2.id "
//			  +   "	WHERE r.id = "+id;
	//	tagName1 = new String[] { "id", "clothescolour","raceName","place","startTime", "type", "league", "remark", "waste", "`name`", "`name`", "endTime","signType","signTime","isRelease","createUser","tid","myScore","opScore","number"};		
//		return sql;
//	}

	
	/**
	* 描述: 通过球队id查询该队所有队员<br/>
	*
	* @return List<UserTeam> 返回类型
	*
	* 创建时间：2017-7-26/下午8:07:28<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public  List<UserTeam> findUserList(Integer id) throws Exception{
		String sql = "SELECT uid FROM user_team ut WHERE tid = "+id;
		List<UserTeam> list = HibernateDAO.findBySql(sql, UserTeam.class);
		return list;
	}
	
	/**
	* 描述: 查找当前用户签到状态<br/>
	*
	* @return Integer 返回类型
	*
	* 创建时间：2017-7-26/下午8:08:23<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public Integer findSignState(Integer uid) throws Exception{
		String sql ="SELECT signUp FROM sign WHERE( raceortrain=2 AND uid="+uid+" AND delState=0)";
		List list = HibernateDAO.findBySql(sql);
		Integer signState = null;
		if(!list.isEmpty()){
			signState = (Integer) list.get(0);
			}
		return signState;
	}
	
	/**
	 * 根据用户id查询最新的一条比赛
	 * @author hujian
	 * @date 2017年7月23日
	 * @version 1.0
	 * @param uid 用户id
	 * @return
	 * @throws Exception 
	 */
	public Object[] getOneRaceByUid(Integer uid) throws Exception {
		String sql = "SELECT r.id,r.raceName,r.startTime,r.endTime,r.place,t.name,t.iconNewName,r.myScore,r.opScore, r.opponent,r.oppoId,r.tid "
				+ " FROM race r "
				+ "	LEFT JOIN teaminfo t ON r.tid = t.id "
				+ "	LEFT JOIN user_team ut ON t.id = ut.tid "
				+ "	LEFT JOIN user u ON u.id = ut.uid "
				+ "	WHERE u.id = "+uid+" AND r.isRelease = 1 AND u.delState = 0 AND t.delState = 0 AND r.delState = 0 "
				+ " ORDER BY r.startTime DESC "
				+ "	LIMIT 0,1";
		List<Object[]> list = findBySql(sql);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
	
	/**
	 * 根据比赛id判断是否已经结束
	 * @author hujian
	 * @date 2017年7月23日
	 * @version 1.0
	 * @param rid 比赛id
	 * @return true 已经结束    false 没有结束
	 * @throws Exception 
	 */
	public boolean checkTimeOutByRid(Integer rid) throws Exception {
		Race race = (Race) findById(Race.class, rid);
		if(race.getEndTime().getTime() < new Date().getTime()){
			//已经结束
			return true;
		}
		return false;
	}
	
	/**
	 * 根据球队id查询未过时的比赛id列表
	 * @author hujian
	 * @date 2017年8月2日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 */
	public List<Race> getNoTimeOutRaceIdsByTid(Integer tid) throws Exception  {
		String sql = "SELECT id FROM race WHERE tid = "+tid+" AND endTime > '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"' AND delState = 0";
		return findBySql(sql, Race.class);
	}
	
	/**
	 * 根据球队id查询比赛id列表
	 * @author hujian
	 * @date 2017年8月2日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 */
	public List<Race> getRaceIdsByTid(Integer tid) throws Exception  {
		String sql = "SELECT id FROM race WHERE tid = "+tid+" AND delState = 0";
		return findBySql(sql, Race.class);
	}

	/**
	 * 描述: ajax查询联赛列表<br/>
	 *
	 * @return List<Object[]> 返回类型
	 *
	 * 创建时间：2017-8-24/下午4:37:24<br/>
	 * @author 高佳伟  
	 * @version V1.0  
	 */
	public List<Object[]> findLeagueListByTid(Integer teamId) throws Exception {
		String sql = "SELECT r.id,r.leagueName,u.nickname FROM user u LEFT JOIN raceleague r ON u.id=r.createUser LEFT JOIN team_league t ON r.id = t.lid WHERE t.tid = "+teamId+" AND u.delState = 0 AND r.delState = 0 AND r.isOpen = 0";
		List<Object[]> list = HibernateDAO.findBySql(sql);
		return list;
	}
	
	/**
	 * 查询加入联赛的全部球队
	 * @author hujian
	 * @date 2017-10-10
	 * @version 1.0
	 * @param lid 联赛id
	 * @param teamId 球队id
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findAllLeagueTeamList(Integer lid,int teamId) throws Exception {
		String sql = "SELECT  t.id,t.name,u.nickname,t.iconNewName FROM raceleague r LEFT JOIN  team_league tlg ON r.id = tlg.lid LEFT JOIN teaminfo t ON tlg.tid = t.id LEFT JOIN user u ON t.captainId = u.id WHERE r.id = "+lid+" AND t.delState=0 AND u.delState=0 AND t.id != "+teamId+" ORDER BY CONVERT (t.name USING gbk) ASC";
		List<Object[]> list = HibernateDAO.findBySql(sql);
		return list;
	}
	
	/**
	 * 根据球队名称后置模糊查询加入联赛的球队列表
	 * @author hujian
	 * @date 2017-10-10
	 * @version 1.0
	 * @param lid 联赛id
	 * @param teamName 球队名称
	 * @param teamId 球队id
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findTeamNameList(Integer lid,String teamName,int teamId) throws Exception {
		String sql = "SELECT  t.id,t.name,u.nickname,t.iconNewName FROM raceleague r LEFT JOIN  team_league tlg ON r.id = tlg.lid LEFT JOIN teaminfo t ON tlg.tid = t.id LEFT JOIN user u ON t.captainId = u.id WHERE t.name LIKE '"+teamName.trim()+"%' AND r.id = "+lid+" AND t.delState=0 AND u.delState=0 AND t.id != "+teamId+" ORDER BY CONVERT (t.name USING gbk) ASC";
		List<Object[]> list = HibernateDAO.findBySql(sql);
		return list;
	}
	
	/**
	 * 查询加入联赛的全部球队（排出根据球队名称后置模糊查询加入联赛的球队列表）
	 * @author hujian
	 * @date 2017-10-10
	 * @version 1.0
	 * @param lid 联赛id
	 * @param teamName 球队名称
	 * @param teamId 球队id
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findExcludeTeamNameList(Integer lid,String teamName,int teamId) throws Exception {
		String sql = "SELECT t.id,t.name,u.nickname,t.iconNewName " +
				" FROM raceleague r " +
				" LEFT JOIN  team_league tlg ON r.id = tlg.lid " +
				" LEFT JOIN teaminfo t ON tlg.tid = t.id " +
				" LEFT JOIN user u ON t.captainId = u.id " +
				" WHERE t.id NOT IN (" +
					" SELECT DISTINCT t.id " +
					" FROM raceleague r " +
					" LEFT JOIN team_league tlg ON r.id = tlg.lid " +
					" LEFT JOIN teaminfo t ON tlg.tid = t.id " +
					" LEFT JOIN user u ON t.captainId = u.id " +
					" WHERE t.name LIKE '"+teamName.trim()+"%' AND r.id = "+lid+" AND t.delState=0 AND u.delState=0" +
				" ) AND r.id = "+lid+" AND t.delState=0 AND u.delState=0 AND t.id != "+teamId+" " +
				" ORDER BY CONVERT (t.name USING gbk) ASC";
		List<Object[]> list = HibernateDAO.findBySql(sql);
		return list;
	}
	
	public String[] getTagName() {
		return tagName;
	}

	public void setTagName(String[] tagName) {
		this.tagName = tagName;
	}


}
