package com.ningxun.apply.dao;

import java.util.List;

import com.ningxun.apply.vo.Apply;
import com.ningxun.apply.vo.ApplyLeague;
import com.ningxun.common.HibernateDAO;
import com.ningxun.league.vo.User;
import com.ningxun.wxuserinfo.vo.WXUser;

/**
* 申请Dao
* @author hujian
* @date 2017年7月20日
* @version 1.0
*/
@SuppressWarnings("all")
public class ApplyDao extends HibernateDAO {
	
	private String tagName[];//json对象的key数组

	/**
	 * 根据球队id获取申请待处理列表
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public List<WXUser> getApplyWaitHandleList(Integer tid) throws Exception{
		String sql = "SELECT u.id,u.nickname,u.headPortraitNew,u.headPortrait "
				+ " FROM user u "
				+ " LEFT JOIN apply a ON u.id = a.uid "
				+ " WHERE a.tid = "+tid+" AND a.applyHandle = 0"
				+ " ORDER BY a.id DESC";
		return findBySql(sql, WXUser.class);
	}
	
	/**
	 * 根据联赛id获取申请待处理列表
	 * @author liudongxin
	 * @date 2018年3月12日
	 * @version 1.0
	 * @param lid 联赛id
	 * @return
	 */
	public List<WXUser> getApplyLeagueWaitHandleList(Integer lid) throws Exception{
		String sql = "SELECT u.id,u.nickname,u.headPortraitNew,u.headPortrait "
				+ " FROM user u "
				+ " LEFT JOIN apply_league alg ON u.id = alg.uid "
				+ " WHERE alg.lid = "+lid+" AND alg.applyHandle = 0 AND alg.delState = 0"
				+ " ORDER BY alg.id DESC";
		return findBySql(sql, WXUser.class);
	}
	
	/**
	 * 根据球队id获取申请已处理列表
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public List<Object[]> getApplyHandleList(Integer tid) throws Exception{
		String sql = getApplyHandleListSql(tid);
		return findBySql(sql);
	}
	
	
	/**
	 * 根据联赛id获取申请请已处理列表
	 * @author liudongxin
	 * @date 2018年3月17日
	 * @version 1.0
	 * @param lid 联赛id
	 * @return
	 */
	public List<Object[]> getApplyLeagueHandleList(Integer lid) throws Exception{
		String sql = "SELECT u.id,u.nickname,u.headPortraitNew,u.headPortrait,alg.applyHandle "+
				"FROM user u "+
				"LEFT JOIN apply_league alg ON u.id = alg.uid "+
				"WHERE alg.lid = "+lid+" AND alg.applyHandle IN (1,2,3) "+
				"ORDER BY alg.handleTime DESC ";
		return findBySql(sql);
	}
	
	/**
	 * 根据联赛id获取申请请已同意列表
	 * @author liudongxin
	 * @date 2018年3月18日
	 * @version 1.0
	 * @param lid 联赛id
	 * @return
	 */
	public List<Object[]> getAgreeHandleList(Integer lid) throws Exception{
		String sql = "SELECT u.id,u.nickname,u.headPortraitNew,u.headPortrait,alg.applyHandle "+
				"FROM user u "+
				"LEFT JOIN apply_league alg ON u.id = alg.uid "+
				"WHERE alg.lid = "+lid+" AND alg.applyHandle = 1 AND alg.delState = 0 "+
				"ORDER BY alg.uid DESC ";
		List<Object[]>list = findBySql(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	
	
	/**
	 * 根据球队id获取申请已处理列表
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public List<Object[]> getApplyHandleList(Integer tid,int pageNo,int pageSize) throws Exception{
		String sql = getApplyHandleListSql(tid);
		return findBySql(sql, pageNo, pageSize);
	}
	
	/**
	 * 根据联赛id获取申请已处理列表
	 * @author liudongxin
	 * @date 2018年3月17日
	 * @version 1.0
	 * @param lid 联赛id
	 * @return
	 */
	public List<Object[]> getApplyLeagueHandleList(Integer lid,int pageNo,int pageSize) throws Exception{
		String sql = getApplyLeagueHandleListSql(lid);
		return findBySql(sql, pageNo, pageSize);
	}
	/**
	 * 获取根据球队id获取申请已处理列表的sql
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public String getApplyHandleListSql(Integer tid) {
		String sql = "SELECT u.id,u.nickname,u.headPortraitNew,u.headPortrait,a.applyHandle "
				+ " FROM user u "
				+ " LEFT JOIN apply a ON u.id = a.uid "
				+ " WHERE a.tid = "+tid+" AND a.applyHandle IN (1,2,3) "
				+ " ORDER BY a.handleTime DESC";
		tagName = new String[] { "id", "nickname","headPortraitNew","headPortrait","applyHandle"};
		return sql;
	}
	
	/**
	 * 获取根据联赛id获取申请已处理列表的sql
	 * @author 
	 * @date 2018年3月12日
	 * @version 1.0
	 * @param lid 联赛id
	 * @return
	 */
	public String getApplyLeagueHandleListSql(Integer lid) {
		String sql = "SELECT u.id,u.nickname,u.headPortraitNew,u.headPortrait,alg.applyHandle "
				+ " FROM user u "
				+ " LEFT JOIN apply_league alg ON u.id = alg.uid "
				+ " WHERE alg.lid = "+lid+" AND alg.applyHandle IN (1,2,3) "
				+ " ORDER BY alg.handleTime DESC";
		tagName = new String[] { "id", "nickname","headPortraitNew","headPortrait","applyHandle"};
		return sql;
	}
	
	/**
	 * 根据用户id和球队id查询申请
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @param uid 用户id
	 * @return
	 */
	public List<Apply> findApplyByUidAndTid(Integer uid, Integer tid) throws Exception{
		String sql = "SELECT * FROM apply WHERE  applyHandle = "+Apply.APPLY_HANDLE_WAITHANDLE+" AND uid = "+uid+" AND tid = "+tid ;
		return findBySql(sql, Apply.class);
	}

	/**
	 * 根据用户id和联赛id查询邀请
	 * @author liudongxin
	 * @date 2018年3月17日
	 * @version 1.0
	 * @param lid 联赛id
	 * @param uid 用户id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ApplyLeague> findApplyLeagueByUidAndLid(Integer uid, Integer lid) throws Exception{
		String sql = "SELECT * FROM apply_league WHERE  applyHandle = "+ApplyLeague.APPLY_HANDLE_WAITHANDLE+" AND uid = "+uid+" AND lid = "+lid ;
		return findBySql(sql,ApplyLeague.class);
	}

	/**
	 * 检查是否已经申请
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @param uid 用户id
	 * @return 返回true：等待处理或者已经同意   false：可以申请
	 * @throws Exception
	 */
	public boolean checkApply(Integer tid, Integer uid) throws Exception{
		String sql = "SELECT id FROM apply WHERE uid = "+uid+" AND tid = "+tid+" AND (applyHandle = "+Apply.APPLY_HANDLE_WAITHANDLE+" OR applyHandle = "+Apply.APPLY_HANDLE_AGREE+")";
		List<Apply> list = findBySql(sql,Apply.class);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 检查是否已经申请加入(联赛)
	 * @author 
	 * @date 2018年3日17
	 * @version 1.0
	 * @param lid 联赛id
	 * @param uid 用户id
	 * @return 返回true：等待处理或者已经同意   false：可以申请
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean checkApplyLeague(Integer lid, Integer uid) throws Exception{
		String sql = "SELECT id FROM apply_league WHERE uid = "+uid+" AND lid = "+lid+" AND (applyHandle = "+ApplyLeague.APPLY_HANDLE_WAITHANDLE+" OR applyHandle = "+ApplyLeague.APPLY_HANDLE_AGREE+")";
		List<ApplyLeague> list = findBySql(sql,ApplyLeague.class);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	
	/**
	 * 检查是否是队长
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @param uid 用户id
	 * @return 返回true：是该球队的队长   false：不是该球队的队长
	 * @throws Exception
	 */
	public boolean checkIsLeader(Integer tid, Integer uid) throws Exception{
		String sql = "SELECT id FROM teaminfo WHERE id = "+tid+" AND captainId = "+uid+" AND delState = 0";
		List<Apply> list = findBySql(sql,Apply.class);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	* 描述: 获取我的申请记录<br/>
	*
	* @return List<Object[]> 返回类型
	*
	* 创建时间：2017-8-8/下午3:42:38<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public List<Object[]> getMyApplyList(Integer uid) throws Exception{
		String sql = "select ti.name, al.applyHandle, ti.iconNewName, " +
				"al.handleTime, u.nickname, al.reason " +
				"from apply al left join teaminfo ti " +
				"on al.tid = ti.id right join user u " +
				"on al.uid = u.id where al.uid = " + uid + " order by al.handleTime desc";
		List<Object[]>list = findBySql(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	
	/**
	 * 
	* 描述: 获取我的申请联赛裁判记录<br/>
	*
	* @return List<Object[]> 返回类型
	*
	* 创建时间：2018-3-17
	* @author   
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getMyApplyLeagueList(Integer uid) throws Exception{
		String sql = "select l.name,alg.applyHandle,u.headPortraitNew,alg.handleTime,u.nickname,alg.reason "+
				"FROM apply_league alg LEFT JOIN league l "+
				"ON alg.lid = l.id RIGHT JOIN user u "+
				"ON alg.uid = u.id WHERE alg.uid = "+uid+" ORDER BY alg.handleTime DESC ";
		List<Object[]>list = findBySql(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	

	/**
	 * 根据用户id和球队id删除同意申请记录
	 * @author hujian
	 * @date 2017年7月27日
	 * @version 1.0
	 * @param uid 用户id
	 * @param tid 球队id
	 */
	public void deleteApplyRecord(Integer uid, Integer tid) throws Exception{
		String sql = "DELETE FROM apply WHERE uid = "+uid+" AND tid = "+tid+" AND applyHandle = "+Apply.APPLY_HANDLE_AGREE;
		this.executeBySql(sql);
	}

	public String[] getTagName() {
		return tagName;
	}

	public void setTagName(String[] tagName) {
		this.tagName = tagName;
	}
}
